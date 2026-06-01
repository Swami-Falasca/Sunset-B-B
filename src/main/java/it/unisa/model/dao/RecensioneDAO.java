package it.unisa.model.dao;

import it.unisa.model.Recensione;
import it.unisa.model.dao.DBManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecensioneDAO {

    // Salva una nuova recensione
    public boolean salvaRecensione(Recensione r) throws SQLException {
        String sql = "INSERT INTO RECENSIONE (id_camera, nome_utente, email_utente, stelle, commento) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, r.getIdCamera());
            ps.setString(2, r.getNomeUtente());
            ps.setString(3, r.getEmailUtente());
            ps.setInt(4, r.getStelle());
            ps.setString(5, r.getCommento());
            return ps.executeUpdate() > 0;
        }
    }

    // Recupera tutte le recensioni di una camera
    public List<Recensione> getByCamera(int idCamera) throws SQLException {
        List<Recensione> lista = new ArrayList<>();
        String sql = "SELECT * FROM RECENSIONE WHERE id_camera = ? ORDER BY data_rec DESC";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCamera);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Recensione rec = new Recensione();
                rec.setId(rs.getInt("id"));
                rec.setIdCamera(rs.getInt("id_camera"));
                rec.setNomeUtente(rs.getString("nome_utente"));
                rec.setEmailUtente(rs.getString("email_utente"));
                rec.setStelle(rs.getInt("stelle"));
                rec.setCommento(rs.getString("commento"));
                rec.setDataRec(rs.getTimestamp("data_rec"));
                lista.add(rec);
            }
        }
        return lista;
    }

    // Recupera tutte le recensioni (per l'admin)
    public List<Recensione> getAll() throws SQLException {
        List<Recensione> lista = new ArrayList<>();
        String sql = "SELECT r.*, c.nome AS nome_camera FROM RECENSIONE r JOIN CAMERA c ON r.id_camera = c.id ORDER BY r.data_rec DESC";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Recensione rec = new Recensione();
                rec.setId(rs.getInt("id"));
                rec.setIdCamera(rs.getInt("id_camera"));
                rec.setNomeUtente(rs.getString("nome_utente"));
                rec.setEmailUtente(rs.getString("email_utente"));
                rec.setStelle(rs.getInt("stelle"));
                rec.setCommento(rs.getString("commento"));
                rec.setDataRec(rs.getTimestamp("data_rec"));
                lista.add(rec);
            }
        }
        return lista;
    }
    
    public boolean eliminaRecensione(int id) throws SQLException {
        String sql = "DELETE FROM RECENSIONE WHERE id = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // Media stelle per una camera
    public double getMediaStelle(int idCamera) throws SQLException {
        String sql = "SELECT AVG(stelle) FROM RECENSIONE WHERE id_camera = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCamera);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        }
        return 0;
    }
}