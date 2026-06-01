package it.unisa.control;

import it.unisa.model.Prodotto;

import it.unisa.model.dao.ProdottoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/prodotti")
public class AdminProdottoListServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verifica login amministratore
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/ADMIN/LoginAdmin.jsp");
            return;
        }

        try {
            ProdottoDAO dao = new ProdottoDAO();
            List<Prodotto> prodotti = dao.findAll();
            request.setAttribute("prodotti", prodotti);
            request.getRequestDispatcher("/jsp/ADMIN/prodotti.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
