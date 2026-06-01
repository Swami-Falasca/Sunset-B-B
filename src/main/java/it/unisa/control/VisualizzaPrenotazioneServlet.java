package it.unisa.control;

import it.unisa.model.Prenotazione;

import it.unisa.model.Utente;
import it.unisa.model.UtenteGoogle;
import it.unisa.model.dao.PrenotazioneDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/VisualizzaPrenotazioneServlet")
public class VisualizzaPrenotazioneServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        // Prova prima dalla sessione
        Prenotazione prenotazione = (Prenotazione) session.getAttribute("prenotazione");

        // Se non è in sessione, la ricarica dal DB (caso logout/login)
        if (prenotazione == null) {
            int idUtente = -1;

            Utente utente = (Utente) session.getAttribute("utente");
            UtenteGoogle utenteGoogle = (UtenteGoogle) session.getAttribute("utenteLoggato");

            if (utente != null) {
                idUtente = utente.getId();
            } else if (utenteGoogle != null) {
                idUtente = utenteGoogle.getId();
            }

            if (idUtente != -1) {
                PrenotazioneDAO dao = new PrenotazioneDAO();
                prenotazione = dao.getPrenotazioneByUtente(idUtente);
                if (prenotazione != null) {
                    session.setAttribute("prenotazione", prenotazione);
                }
            }
        }

        if (prenotazione == null) {
            // Nessuna prenotazione → pagina apposita
            request.getRequestDispatcher("/jsp/PRENOTAZIONE/NessunaPrenot.jsp")
                   .forward(request, response);
        } else {
            request.getRequestDispatcher("/jsp/PRENOTAZIONE/VisualizzaPrenotazione.jsp")
                   .forward(request, response);
        }
    }
}