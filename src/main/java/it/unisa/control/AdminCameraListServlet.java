package it.unisa.control;

import it.unisa.model.Camera;

import it.unisa.model.dao.CameraDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/camere")
public class AdminCameraListServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/ADMIN/LoginAdmin.jsp");
            return;
        }
        try {
            CameraDAO dao = new CameraDAO();
            List<Camera> camere = dao.findAll();
            request.setAttribute("camere", camere);
            request.getRequestDispatcher("/jsp/ADMIN/camere.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}