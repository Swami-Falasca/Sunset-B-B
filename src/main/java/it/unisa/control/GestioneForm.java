package it.unisa.control;

import it.unisa.model.Admin;
import it.unisa.model.Utente;
import it.unisa.model.Prenotazione;
import it.unisa.model.dao.AdminDAO;
import it.unisa.model.dao.UtenteDAO;
import it.unisa.model.dao.PrenotazioneDAO;
import it.unisa.util.PasswordUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/GestioneForm")
public class GestioneForm extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            request.setAttribute("errore", "Email e password sono obbligatori.");
            request.getRequestDispatcher("/jsp/ACCEDI/Login.jsp").forward(request, response);
            return;
        }

        // 1. Controlla se è un admin
        try {
            AdminDAO adminDAO = new AdminDAO();
            String hashedPassword = PasswordUtils.hashPassword(password);
            Admin admin = adminDAO.loginByEmail(email, hashedPassword);
            if (admin != null) {
                HttpSession session = request.getSession();
                session.setAttribute("admin", admin);
                session.setAttribute("adminLogged", true);
                session.setAttribute("adminName", admin.getUsername());
                response.sendRedirect(request.getContextPath() + "/jsp/ADMIN/Dashboard.jsp");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. Prova come utente normale
        UtenteDAO dao = new UtenteDAO();
        Utente utente = dao.login(email, password);

        if (utente != null) {
            HttpSession session = request.getSession();
            session.setAttribute("utente", utente);

            PrenotazioneDAO prenDAO = new PrenotazioneDAO();
            Prenotazione prenotazione = prenDAO.getPrenotazioneByUtente(utente.getId());
            if (prenotazione != null) {
                session.setAttribute("prenotazione", prenotazione);
            }
            
            
   

         // Gestione recensione pendente (salvata prima del login)
         String pendingId = (String) session.getAttribute("pendingIdCamera");
         String pendingStelle = (String) session.getAttribute("pendingStelle");
         String pendingCommento = (String) session.getAttribute("pendingCommento");
         String pendingCitta = (String) session.getAttribute("pendingCitta");

         if (pendingId != null && pendingStelle != null) {
             try {
                 it.unisa.model.Recensione rec = new it.unisa.model.Recensione();
                 rec.setIdCamera(Integer.parseInt(pendingId));
                 rec.setNomeUtente(utente.getNome() + " " + utente.getCognome());
                 rec.setEmailUtente(utente.getEmail());
                 rec.setStelle(Integer.parseInt(pendingStelle));
                 rec.setCommento(pendingCommento != null ? pendingCommento : "");
                 new it.unisa.model.dao.RecensioneDAO().salvaRecensione(rec);
                 session.setAttribute("recensioneInviata", "ok");
             } catch (Exception ex) {
                 ex.printStackTrace();
             }
             session.removeAttribute("pendingIdCamera");
             session.removeAttribute("pendingStelle");
             session.removeAttribute("pendingCommento");
             // Forza il redirect alla pagina della città
             if (pendingCitta != null) {
                 session.removeAttribute("pendingCitta");
                 session.removeAttribute("redirectAfterLogin");
                 response.sendRedirect(request.getContextPath() + "/jsp/CAMERE/Bnb.jsp?città=" + pendingCitta);
                 return;
             }
         }

            // ✅ Se l'utente stava andando al Pagamento, lo rimandiamo lì
            String redirectUrl = (String) session.getAttribute("redirectAfterLogin");
            if (redirectUrl != null) {
                session.removeAttribute("redirectAfterLogin");
                response.sendRedirect(request.getContextPath() + redirectUrl);
            } else {
                response.sendRedirect(request.getContextPath() + "/jsp/HOME/Home.jsp");
            }

        } else {
            request.setAttribute("errore", "Email o password non corretti.");
            request.getRequestDispatcher("/jsp/ACCEDI/Login.jsp").forward(request, response);
        }
    }
}