package it.unisa.control;



import jakarta.servlet.annotation.WebServlet;



import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.*;
import java.nio.file.Paths;
import java.sql.Date;

import it.unisa.model.Utente;
import it.unisa.model.dao.UtenteDAO;
import it.unisa.util.*;


@WebServlet("/HOME/GestioneFormRegistrati")

@MultipartConfig(
	    maxFileSize = 10 * 1024 * 1024,
	    maxRequestSize = 15 * 1024 * 1024
	)
public class GestioneFormRegistrati extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    // ← PRIMA leggi il file
	    Part filePart = request.getPart("file_pdf");
	    String nomeFile = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

	    // ← POI leggi tutti gli altri parametri
	    String nome = request.getParameter("nome");
	    String cognome = request.getParameter("cognome");
	    String dataNascita = request.getParameter("data_nascita");
	    String tipoDocumento = request.getParameter("tipo_documento");
	    String numeroDocumento = request.getParameter("numero_documento");
	    String residenza = request.getParameter("residenza");
	    String email = request.getParameter("email");
	    String password = request.getParameter("password");

	    if (password == null || password.isEmpty()) {
	        request.setAttribute("errore", "Password non ricevuta dal form.");
	        request.getRequestDispatcher("/jsp/HOME/Registrati.jsp").forward(request, response);
	        return;
	    }

	    String hashedPassword = PasswordUtils.hashPassword(password);

	    

          // Imposta solo il nome del file


        // 2. Crea oggetto Utente
        Utente utente = new Utente();
        utente.setNome(nome);
        utente.setCognome(cognome);
        utente.setDataNascita(Date.valueOf(dataNascita));
        utente.setTipoDocumento(tipoDocumento);
        utente.setNumeroDocumento(numeroDocumento);
        utente.setNome_file(nomeFile);;
        utente.setResidenza(residenza);
        utente.setEmail(email);
        utente.setPassword(hashedPassword);
 
        // 3. Chiama DAO
        UtenteDAO dao = new UtenteDAO();
        try {
            boolean inserito = dao.registraUtente(utente);
            if (inserito) {
                // Salva utente in sessione così è loggato subito dopo la registrazione
                HttpSession session = request.getSession();
                session.setAttribute("utente", utente);
                response.sendRedirect(request.getContextPath() + "/jsp/HOME/Home.jsp");
                return;
            } else {
                request.setAttribute("errore", "Registrazione non riuscita.");
                request.getRequestDispatcher("/jsp/HOME/Registrati.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errore", "Errore durante la registrazione: " + e.getMessage());
            request.getRequestDispatcher("/jsp/HOME/Registrati.jsp").forward(request, response);
        }
    }
}