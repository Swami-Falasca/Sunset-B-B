package it.unisa.control;

import it.unisa.model.Prenotazione;

import it.unisa.model.dao.PrenotazioneDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/ordini")
public class AdminOrdiniServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/ADMIN/LoginAdmin.jsp");
            return;
        }
        try {
            PrenotazioneDAO dao = new PrenotazioneDAO();
            List<Prenotazione> prenotazioni = dao.findAll();
            request.setAttribute("prenotazioni", prenotazioni);
            request.getRequestDispatcher("/jsp/ADMIN/ordini.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}