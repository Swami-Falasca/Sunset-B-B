package it.unisa.control;

import it.unisa.model.Utente;

import it.unisa.model.UtenteGoogle;
import it.unisa.model.dao.UtenteDAO;
import it.unisa.model.dao.UtenteGoogleDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/VisualizzaProfiloServlet")
public class VisualizzaProfiloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        Utente utente = (Utente) session.getAttribute("utente");
        UtenteGoogle utenteGoogle = (UtenteGoogle) session.getAttribute("utenteLoggato");

        if (utente != null) {
            // Ricarica dal DB per avere dati aggiornati
            UtenteDAO dao = new UtenteDAO();
            Utente aggiornato = dao.findByEmail(utente.getEmail());
            request.setAttribute("profiloUtente", aggiornato);
            request.setAttribute("tipoUtente", "normale");

        } else if (utenteGoogle != null) {
            try {
                UtenteGoogleDAO dao = new UtenteGoogleDAO();
                UtenteGoogle aggiornato = dao.getUtenteByEmail(utenteGoogle.getEmail());
                request.setAttribute("profiloGoogle", aggiornato);
                request.setAttribute("tipoUtente", "google");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            // Nessuno loggato
            response.sendRedirect(request.getContextPath() + "/jsp/ACCEDI/Login.jsp");
            return;
        }

        request.getRequestDispatcher("/jsp/PROFILO/Profilo.jsp")
               .forward(request, response);
    }
}