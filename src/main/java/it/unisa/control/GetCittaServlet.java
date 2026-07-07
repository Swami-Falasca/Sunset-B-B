package it.unisa.control;
import it.unisa.model.dao.DBManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/GetCittaServlet")
public class GetCittaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONArray cittaArray = new JSONArray();
        try (Connection conn = DBManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT id, nome, regione FROM citta ORDER BY nome");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                JSONObject citta = new JSONObject();
                citta.put("id", rs.getInt("id"));
                citta.put("nome", rs.getString("nome"));
                citta.put("regione", rs.getString("regione"));
                cittaArray.put(citta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        PrintWriter out = response.getWriter();
        out.print(cittaArray.toString());
        out.flush();
    }
}
