package it.unisa.control;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;



import org.json.JSONObject; // Assicurati che questa libreria sia inclusa

import it.unisa.model.UtenteGoogle;
import it.unisa.model.dao.UtenteGoogleDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;



@WebServlet("/oauth2callback")
public class OAuth2CallbackServlet extends HttpServlet {
    private static final String CLIENT_ID = "489957560905-2fn9en5cc1agt4e53u0k93avsfmkibe5.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "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";
    private static final String REDIRECT_URI = "http://localhost/SunsetBnB/oauth2callback";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");

        // Richiedi token accesso da Google
        URL url = new URL("https://oauth2.googleapis.com/token");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        String data = "code=" + code +
                      "&client_id=" + CLIENT_ID +
                      "&client_secret=" + CLIENT_SECRET +
                      "&redirect_uri=" + REDIRECT_URI +
                      "&grant_type=authorization_code";
        HttpSession session = request.getSession();

        try (OutputStream os = conn.getOutputStream()) {
            os.write(data.getBytes());
        }

        // Leggi la risposta (con token)
        BufferedReader in;
        if (conn.getResponseCode() == 200) {
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            in = new BufferedReader(new InputStreamReader(conn.getErrorStream())); // <<<<<<
        }

        StringBuilder responseText = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            responseText.append(line);
        }
        in.close();

        System.out.println("TOKEN RESPONSE: " + responseText.toString());
        // PARSA LA RISPOSTA JSON E OTTIENI I DATI UTENTE
        try {
            JSONObject json = new JSONObject(responseText.toString());
            String accessToken = json.getString("access_token");

            // Chiedi info utente a Google
            URL userInfoUrl = new URL("https://www.googleapis.com/oauth2/v2/userinfo");
            HttpURLConnection userInfoConn = (HttpURLConnection) userInfoUrl.openConnection();
            userInfoConn.setRequestMethod("GET");
            userInfoConn.setRequestProperty("Authorization", "Bearer " + accessToken);

            BufferedReader reader = new BufferedReader(new InputStreamReader(userInfoConn.getInputStream()));
            StringBuilder userInfo = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                userInfo.append(line);
            }
            reader.close();

         // Salva i dati utente in sessione
            request.getSession().setAttribute("userInfo", userInfo.toString());
            JSONObject user = new JSONObject(userInfo.toString());
            String nome = user.getString("name");
            String email = user.getString("email");
            String immagine = user.getString("picture");
            
            try {
                UtenteGoogleDAO dao = new UtenteGoogleDAO();
                if (!dao.utenteEsiste(email)) {
                    dao.salvaUtenteGoogle(nome, email, immagine);
                    System.out.println("✅ Utente Google inserito nel DB");
                } else {
                    System.out.println("ℹ Utente già presente nel DB");
                }
                UtenteGoogle utente = dao.getUtenteByEmail(email);
                request.getSession().setAttribute("utenteLoggato", utente);
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("❌ Errore salvataggio utente Google nel DB");
            }

            // Redirect a una pagina JSP di benvenuto
          //  response.sendRedirect("HOME/Home.jsp"); //


        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("erroreOAuth", true);
        }

        // Redirect finale (unico punto in cui viene fatto)
 
        String redirectUrl = (String) session.getAttribute("redirectAfterLogin");
        if (redirectUrl != null) {
            session.removeAttribute("redirectAfterLogin");
            response.sendRedirect(request.getContextPath() + "/" + redirectUrl);
        } else {
            response.sendRedirect(request.getContextPath() + "/jsp/HOME/Home.jsp");
        }



    }
}