package it.unisa.model.dao;


import it.unisa.model.Ordine;

import it.unisa.model.Prodotto;
import it.unisa.model.dao.DBManager;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrdineDAO {

    public List<Ordine> findByDateAndCliente(Date from, Date to, Integer idCliente) throws SQLException {
        List<Ordine> ordini = new ArrayList<>();

        String query = "SELECT * FROM ordine WHERE data_ordine BETWEEN ? AND ?";
        if (idCliente != null) {
            query += " AND id_cliente = ?";
        }

        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setDate(1, from);
            ps.setDate(2, to);

            if (idCliente != null) {
                ps.setInt(3, idCliente);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Ordine o = new Ordine();
                o.setId(rs.getInt("id"));
                o.setIdCliente(rs.getInt("id_cliente"));
                o.setDataOrdine(rs.getTimestamp("data_ordine").toLocalDateTime());
                o.setTotale(rs.getDouble("totale"));
                ordini.add(o);
            }
        }

        return ordini;
    }
    
    public void salvaOrdine(int idCliente, LocalDateTime data, double totale) {
    	String sql = "INSERT INTO ordine (id_cliente, data_ordine, totale) VALUES (?, ?, ?)";


        try (Connection con = DBManager.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            stmt.setTimestamp(2, Timestamp.valueOf(data));
            stmt.setDouble(3, totale);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); // oppure logga l'errore
            throw new RuntimeException("Errore durante il salvataggio dell'ordine", e);
        }
    }
    
}