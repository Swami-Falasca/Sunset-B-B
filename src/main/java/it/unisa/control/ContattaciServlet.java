package it.unisa.control;
import java.io.IOException;


import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ContattaciServlet")
public class ContattaciServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String messaggio = request.getParameter("messaggio");

        // Impostazioni email
        String destinatario = "sunset.bnb25@gmail.com";
        final String username = "sunset.bnb25@gmail.com";
        final String password = "otad nfeh pqrn drbr";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(email));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            msg.setSubject("Nuovo messaggio da " + nome);
            msg.setText("Nome: " + nome + "\nEmail: " + email + "\nMessaggio:\n" + messaggio);
            Transport.send(msg);
            response.sendRedirect(request.getContextPath() + "/jsp/HOME/Contatti.jsp?successo=true");
            response.getWriter().println("Messaggio inviato con successo!");
        } catch (MessagingException e) {
            throw new ServletException("Errore durante l'invio dell'email", e);
        }
    }
}