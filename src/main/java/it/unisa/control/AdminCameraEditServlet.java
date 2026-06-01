package it.unisa.control;

import it.unisa.model.Camera;

import it.unisa.model.dao.CameraDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/modificaCamera")
public class AdminCameraEditServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/ADMIN/LoginAdmin.jsp");
            return;
        }
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            CameraDAO dao = new CameraDAO();
            Camera camera = dao.findById(id);
            request.setAttribute("camera", camera);
            request.getRequestDispatcher("/jsp/ADMIN/modificaCamera.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/ADMIN/LoginAdmin.jsp");
            return;
        }
        try {
            Camera c = new Camera();
            c.setId(Integer.parseInt(request.getParameter("id")));
            c.setNome(request.getParameter("nome"));
            c.setDescrizione(request.getParameter("descrizione"));
            c.setExtra(request.getParameter("extra"));
            c.setPrezzo(Double.parseDouble(request.getParameter("prezzo")));

            CameraDAO dao = new CameraDAO();
            dao.update(c);
            response.sendRedirect(request.getContextPath() + "/admin/camere");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}