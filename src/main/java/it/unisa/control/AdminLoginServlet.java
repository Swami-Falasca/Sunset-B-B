package it.unisa.control;

import it.unisa.model.Admin;


import it.unisa.util.PasswordUtils;

import it.unisa.model.dao.AdminDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/login")
public class AdminLoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            // Hash la password inserita dall’utente
            String hashedPassword = PasswordUtils.hashPassword(password);

            AdminDAO dao = new AdminDAO();
            Admin admin = dao.login(username, hashedPassword);

            if (admin != null) {
                HttpSession session = request.getSession();
                session.setAttribute("admin", admin);  // per eventuali usi futuri
                session.setAttribute("adminLogged", true);  // flag login
                session.setAttribute("adminName", admin.getUsername());  // nome admin per mostrare nel JSP
                response.sendRedirect(request.getContextPath() + "/jsp/ADMIN/Dashboard.jsp");
            }else {
                // login fallito
                request.setAttribute("error", "Username o password errati");
                request.getRequestDispatcher("/jsp/ADMIN/LoginAdmin.jsp").forward(request, response);
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
