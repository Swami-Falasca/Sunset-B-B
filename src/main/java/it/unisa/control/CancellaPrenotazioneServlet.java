package it.unisa.control;

import it.unisa.model.Prenotazione;

import it.unisa.model.dao.PrenotazioneDAO;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/CancellaPrenotazioneServlet")
public class CancellaPrenotazioneServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Prenotazione prenotazione = (Prenotazione) session.getAttribute("prenotazione");

        if (prenotazione != null) {
            PrenotazioneDAO prenDAO = new PrenotazioneDAO();
            prenDAO.cancellaPrenotazioneById(prenotazione.getId());
        }

        // Pulisco la sessione
        session.removeAttribute("prenotazione");

        // Redirect alla home
        response.sendRedirect(request.getContextPath() + "/jsp/HOME/Home.jsp");
    }
}
