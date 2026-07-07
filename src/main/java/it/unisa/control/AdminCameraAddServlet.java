package it.unisa.control;

import it.unisa.model.Camera;
import it.unisa.model.dao.CameraDAO;
import it.unisa.model.dao.DBManager;
import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet("/admin/aggiungiCamera")
@MultipartConfig(
        maxFileSize = 10 * 1024 * 1024,      // 10 MB per file
        maxRequestSize = 60 * 1024 * 1024,   // 60 MB totali per richiesta
        fileSizeThreshold = 1024 * 1024
)
public class AdminCameraAddServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/ACCEDI/Login.jsp");
            return;
        }
        try {
            Map<Integer, String> cittaList = new LinkedHashMap<>();
            String sql = "SELECT id, nome FROM citta ORDER BY nome";
            try (Connection conn = DBManager.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    cittaList.put(rs.getInt("id"), rs.getString("nome"));
                }
            }
            request.setAttribute("cittaList", cittaList);
            request.getRequestDispatcher("/jsp/ADMIN/aggiungiCamera.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/ACCEDI/Login.jsp");
            return;
        }
        try {
            int idCitta = Integer.parseInt(request.getParameter("idCitta"));

            // Serve il nome della città per sapere in quale cartella salvare le immagini
            String nomeCitta = getNomeCitta(idCitta);
            if (nomeCitta == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Città non valida");
                return;
            }

            // Cartella dove Bnb.jsp va già a cercare le immagini: /images/IMMAGINI-BNB/<Città>/
            String cartellaRelativa = "/images/IMMAGINI-BNB/" + nomeCitta + "/";
            String realPath = getServletContext().getRealPath(cartellaRelativa);
            File dir = new File(realPath);
            if (!dir.exists()) dir.mkdirs();

            StringBuilder nomiFile = new StringBuilder();
            for (Part part : request.getParts()) {
                if (!"immagini".equals(part.getName())) continue;
                if (part.getSize() <= 0) continue;

                String submitted = part.getSubmittedFileName();
                if (submitted == null || submitted.trim().isEmpty()) continue;

                String fileName = Paths.get(submitted).getFileName().toString();
                part.write(realPath + File.separator + fileName);

                if (nomiFile.length() > 0) nomiFile.append(",");
                nomiFile.append(fileName);
            }

            Camera c = new Camera();
            c.setNome(request.getParameter("nome"));
            c.setDescrizione(request.getParameter("descrizione"));
            c.setExtra(request.getParameter("extra"));
            c.setPrezzo(Double.parseDouble(request.getParameter("prezzo")));
            c.setImmagini(nomiFile.toString());
            c.setIdCitta(idCitta);

            CameraDAO dao = new CameraDAO();
            dao.insert(c);

            response.sendRedirect(request.getContextPath() + "/admin/camere");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private String getNomeCitta(int idCitta) throws SQLException {
        String sql = "SELECT nome FROM citta WHERE id = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCitta);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString("nome");
            }
        }
        return null;
    }
}