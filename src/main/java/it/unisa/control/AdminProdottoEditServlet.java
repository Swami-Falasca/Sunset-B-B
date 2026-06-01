package it.unisa.control;

import it.unisa.model.Prodotto;

import it.unisa.model.dao.ProdottoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/admin/modificaProdotto")
@MultipartConfig
public class AdminProdottoEditServlet extends HttpServlet {

    // GET → mostra il form con i dati attuali
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/ADMIN/LoginAdmin.jsp");
            return;
        }

        int id = Integer.parseInt(request.getParameter("id"));

        try {
            ProdottoDAO dao = new ProdottoDAO();
            Prodotto prodotto = dao.findById(id);
            request.setAttribute("prodotto", prodotto);
            request.getRequestDispatcher("/jsp/ADMIN/modificaProdotto.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    // POST → salva modifiche nel DB
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/ADMIN/LoginAdmin.jsp");
            return;
        }

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String nome = request.getParameter("nome");
            String descrizione = request.getParameter("descrizione");
            double prezzo = Double.parseDouble(request.getParameter("prezzo"));
            int disponibilita = Integer.parseInt(request.getParameter("disponibilita"));

            Prodotto prodotto = new Prodotto();
            prodotto.setId(id);
            prodotto.setNome(nome);
            prodotto.setDescrizione(descrizione);
            prodotto.setPrezzo(prezzo);
            prodotto.setDisponibilita(disponibilita);
            
            
            List<String> immagini = new ArrayList<>();
            for (Part part : request.getParts()) {
                if ("immagine".equals(part.getName()) && part.getSize() > 0) {
                    String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                    String uploadPath = getServletContext().getRealPath("/") + "uploads";
                    Files.createDirectories(Paths.get(uploadPath));
                    String filePath = uploadPath + File.separator + fileName;
                    part.write(filePath);
                    immagini.add("uploads/" + fileName); // salva percorso relativo
                }
            }
            prodotto.setImmagine(String.join(";", immagini));
            ProdottoDAO dao = new ProdottoDAO();
            dao.update(prodotto);

            response.sendRedirect(request.getContextPath() + "/admin/prodotti");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
