package it.unisa.control;

import it.unisa.model.Prodotto;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import it.unisa.model.dao.ProdottoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;

@WebServlet("/admin/aggiungiProdotto")
@MultipartConfig
public class AdminProdottoAddServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verifica accesso admin
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/ADMIN/LoginAdmin.jsp");
            return;
        }

        // Ottieni parametri
        String nome = request.getParameter("nome");
        String descrizione = request.getParameter("descrizione");
        double prezzo = Double.parseDouble(request.getParameter("prezzo"));
        int disponibilita = Integer.parseInt(request.getParameter("disponibilita"));
     
        // Upload file immagine
        List<String> immagini = new ArrayList<>();
        for (Part part : request.getParts()) {
            if ("immagine".equals(part.getName()) && part.getSize() > 0) {
                String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                String uploadPath = getServletContext().getRealPath("/") + "uploads";
                Files.createDirectories(Paths.get(uploadPath));
                String filePath = uploadPath + File.separator + fileName;
                part.write(filePath);
                immagini.add("uploads/" + fileName); // salva percorso relativo
            }
        }

        
        
        // Crea prodotto e salva
        Prodotto prodotto = new Prodotto();
        prodotto.setNome(nome);
        prodotto.setDescrizione(descrizione);
        prodotto.setPrezzo(prezzo);
        prodotto.setDisponibilita(disponibilita);
        prodotto.setImmagine(String.join(";", immagini));

      

        try {
            ProdottoDAO dao = new ProdottoDAO();
            dao.save(prodotto);
            response.sendRedirect(request.getContextPath() + "/admin/prodotti");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
