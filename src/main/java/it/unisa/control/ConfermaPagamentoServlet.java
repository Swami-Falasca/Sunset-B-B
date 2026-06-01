package it.unisa.control;

import it.unisa.model.Prenotazione;

import it.unisa.model.Utente;
import it.unisa.model.UtenteGoogle;
import it.unisa.model.dao.OrdineDAO;
import it.unisa.model.dao.PrenotazioneDAO;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/ConfermaPagamentoServlet")
public class ConfermaPagamentoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Prenotazione prenotazione = (Prenotazione) session.getAttribute("prenotazione");

        Utente utente = (Utente) session.getAttribute("utente");
        UtenteGoogle utenteGoogle = (UtenteGoogle) session.getAttribute("utenteLoggato");

        if (prenotazione == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/HOME/Home.jsp");
            return;
        }

        PrenotazioneDAO prenDAO = new PrenotazioneDAO();
        OrdineDAO ordineDAO = new OrdineDAO();

        // Utente registrato manualmente
        if (utente != null) {
            ordineDAO.salvaOrdine(utente.getId(), LocalDateTime.now(), prenotazione.getPrezzoTotale());
            prenDAO.salvaPrenotazione(prenotazione, utente.getId(), utente.getNome(), utente.getCognome());
        }
        // Utente loggato con Google
        else if (utenteGoogle != null) {
            ordineDAO.salvaOrdine(utenteGoogle.getId(), LocalDateTime.now(), prenotazione.getPrezzoTotale());

            // Separiamo nome e cognome
            String fullName = utenteGoogle.getNome(); 
            String nome = "";
            String cognome = "";

            if (fullName != null && !fullName.isEmpty()) {
                String[] parts = fullName.split(" ", 2); // divide in due parti
                nome = parts[0]; // "Federica"
                cognome = (parts.length > 1) ? parts[1] : ""; // "De Simone"
            }

            prenDAO.salvaPrenotazione(prenotazione, utenteGoogle.getId(), nome, cognome);
        }

        request.setAttribute("successo", true);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/PRENOTAZIONE/confermaPagamento.jsp");
        dispatcher.forward(request, response);
    }
}
