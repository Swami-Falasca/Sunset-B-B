package it.unisa.control;
import it.unisa.model.Recensione;
import it.unisa.model.dao.RecensioneDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/recensioni")
public class AdminRecensioniServlet extends HttpServlet {

    // GET → mostra lista recensioni
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/ADMIN/LoginAdmin.jsp");
            return;
        }
        try {
            RecensioneDAO dao = new RecensioneDAO();
            List<Recensione> recensioni = dao.getAll();
            request.setAttribute("recensioni", recensioni);
            request.getRequestDispatcher("/jsp/ADMIN/recensioni.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    // POST → elimina recensione
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/ADMIN/LoginAdmin.jsp");
            return;
        }
        try {
            int id = Integer.parseInt(request.getParameter("idRecensione"));
            RecensioneDAO dao = new RecensioneDAO();
            dao.eliminaRecensione(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/admin/recensioni");
    }
}