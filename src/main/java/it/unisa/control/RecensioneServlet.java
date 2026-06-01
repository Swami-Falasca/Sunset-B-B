package it.unisa.control;
import it.unisa.model.Recensione;
import it.unisa.model.Utente;
import it.unisa.model.UtenteGoogle;
import it.unisa.model.dao.RecensioneDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

@WebServlet("/InviaRecensione")
public class RecensioneServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);

        // Recupera utente dalla sessione (login normale o Google)
        String nomeUtente = null;
        String emailUtente = null;
        if (session != null) {
            Utente u = (Utente) session.getAttribute("utente");
            if (u != null) {
                nomeUtente = u.getNome() + " " + u.getCognome();
                emailUtente = u.getEmail();
            } else {
                UtenteGoogle ug = (UtenteGoogle) session.getAttribute("utenteLoggato");
                if (ug != null) {
                    nomeUtente = ug.getNome();
                    emailUtente = ug.getEmail();
                }
            }
        }

        // Leggi città provando entrambi i nomi (con e senza accento)
        String citta = request.getParameter("citta");
        if (citta == null) citta = request.getParameter("città");
        if (citta != null) citta = URLDecoder.decode(citta, "UTF-8");
        String cittaEncoded = citta != null ? citta.replace(" ", "+") : "";

        // Utente non loggato → salva dati in sessione e manda al login
        if (nomeUtente == null) {
            if (session == null) session = request.getSession(true);
            session.setAttribute("redirectAfterLogin", "/jsp/CAMERE/Bnb.jsp?citta=" + cittaEncoded);
            session.setAttribute("pendingIdCamera", request.getParameter("idCamera"));
            session.setAttribute("pendingStelle", request.getParameter("stelle"));
            session.setAttribute("pendingCommento", request.getParameter("commento"));
            session.setAttribute("pendingCitta", citta);
            response.sendRedirect(request.getContextPath() + "/jsp/ACCEDI/Login.jsp");
            return;
        }

        // Utente loggato → salva recensione
        try {
            int idCamera = Integer.parseInt(request.getParameter("idCamera"));
            int stelle   = Integer.parseInt(request.getParameter("stelle"));
            String commento = request.getParameter("commento");

            Recensione rec = new Recensione();
            rec.setIdCamera(idCamera);
            rec.setNomeUtente(nomeUtente);
            rec.setEmailUtente(emailUtente);
            rec.setStelle(stelle);
            rec.setCommento(commento != null ? commento : "");

            new RecensioneDAO().salvaRecensione(rec);
            session.setAttribute("recensioneInviata", "ok");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("recensioneInviata", "errore");
        }

        response.sendRedirect(request.getContextPath() 
        	    + "/jsp/CAMERE/Bnb.jsp?citt%C3%A0=" + cittaEncoded);
    }
}