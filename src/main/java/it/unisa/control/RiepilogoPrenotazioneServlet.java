package it.unisa.control;

import it.unisa.model.Prenotazione;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

@WebServlet("/RiepilogoPrenotazioneServlet")
public class RiepilogoPrenotazioneServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recupero parametri
        String citta = request.getParameter("citta");
        String nomeBnb = request.getParameter("nomeBnb");
        String imgBnb = request.getParameter("imgBnb");
        String checkinStr = request.getParameter("checkin");
        String checkoutStr = request.getParameter("checkout");
        String adultiStr = request.getParameter("adulti");
        String bambiniStr = request.getParameter("bambini");
        String camereStr = request.getParameter("camere");

        // Validazione base
        if (citta == null || nomeBnb == null || imgBnb == null ||
            checkinStr == null || checkoutStr == null ||
            adultiStr == null || bambiniStr == null || camereStr == null ||
            citta.isEmpty() || nomeBnb.isEmpty() || imgBnb.isEmpty() ||
            checkinStr.isEmpty() || checkoutStr.isEmpty() ||
            adultiStr.isEmpty() || bambiniStr.isEmpty() || camereStr.isEmpty()) {

            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tutti i campi sono obbligatori.");
            return;
        }

        try {
            // Parsing delle date con formato dd/MM/yyyy
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate checkin = LocalDate.parse(checkinStr, formatter);
            LocalDate checkout = LocalDate.parse(checkoutStr, formatter);

            // Parsing dei numeri
            int adulti = Integer.parseInt(adultiStr);
            int bambini = Integer.parseInt(bambiniStr);
            int camere = Integer.parseInt(camereStr);

            // Validazione logica delle date
            long notti = ChronoUnit.DAYS.between(checkin, checkout);
            if (notti <= 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "La data di checkout deve essere successiva al check-in.");
                return;
            }

            // Calcolo prezzo
            double prezzoPerNotte = 80.0; // Può essere dinamico in futuro
            double prezzoTotale = prezzoPerNotte * notti;

            // Creazione oggetto Prenotazione
            Prenotazione prenotazione = new Prenotazione();
            prenotazione.setCitta(citta);
            prenotazione.setNomeBnb(nomeBnb);
            prenotazione.setImmagineBnb(imgBnb);
            prenotazione.setCheckin(checkin);
            prenotazione.setCheckout(checkout);
            prenotazione.setAdulti(adulti);
            prenotazione.setBambini(bambini);
            prenotazione.setCamere(camere);
            prenotazione.setPrezzoTotale(prezzoTotale);

            // Salvataggio in sessione
            HttpSession session = request.getSession();
            session.setAttribute("prenotazione", prenotazione);

            // Redirect alla pagina riepilogo
            response.sendRedirect(request.getContextPath() + "/jsp/PRENOTAZIONE/riepilogoPrenotazione.jsp");


        } catch (DateTimeParseException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Formato data non valido. Usa il formato gg/mm/aaaa.");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "I valori numerici non sono validi.");
        }
    }
}
