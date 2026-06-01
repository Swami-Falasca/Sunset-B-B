package it.unisa.model.dao;



import java.sql.*;

import java.util.ArrayList;
import java.util.List;

import it.unisa.model.Citta;

public class CittaDAO {

    public static List<Citta> getAllCitta() throws SQLException {
        List<Citta> cittaList = new ArrayList<>();
        String query = "SELECT nome, regione FROM citta ORDER BY nome";

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Citta c = new Citta();
                c.setNome(rs.getString("nome"));
                c.setRegione(rs.getString("regione"));
                cittaList.add(c);
            }
        }
        return cittaList;
    }
}

