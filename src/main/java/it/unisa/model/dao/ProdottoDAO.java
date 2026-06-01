package it.unisa.model.dao;

import it.unisa.model.Prodotto;

import java.sql.*;
import java.util.*;

public class ProdottoDAO {

    private Connection getConnection() throws SQLException {
        return DBManager.getConnection(); // Assicurati che funzioni
    }

    public void save(Prodotto prodotto) throws SQLException {
    	String sql = "INSERT INTO prodotto (nome, descrizione, prezzo, disponibilita, immagine) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, prodotto.getNome());
            ps.setString(2, prodotto.getDescrizione());
            ps.setDouble(3, prodotto.getPrezzo());
            ps.setInt(4, prodotto.getDisponibilita());
            ps.setString(5, prodotto.getImmagine());

            ps.executeUpdate();
        }
    }

    public List<Prodotto> findAll() throws SQLException {
        List<Prodotto> list = new ArrayList<>();
        String sql = "SELECT * FROM prodotto WHERE eliminato = FALSE";

        try (Connection con = getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Prodotto p = new Prodotto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setDescrizione(rs.getString("descrizione"));
                p.setPrezzo(rs.getDouble("prezzo"));
                p.setDisponibilita(rs.getInt("disponibilita"));
                p.setEliminato(rs.getBoolean("eliminato"));
                p.setImmagine(rs.getString("immagine"));


                list.add(p);
            }
        }
        return list;
    }

    public Prodotto findById(int id) throws SQLException {
        String sql = "SELECT * FROM prodotto WHERE id = ?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Prodotto p = new Prodotto();
                    p.setId(rs.getInt("id"));
                    p.setNome(rs.getString("nome"));
                    p.setDescrizione(rs.getString("descrizione"));
                    p.setPrezzo(rs.getDouble("prezzo"));
                    p.setDisponibilita(rs.getInt("disponibilita"));
                    p.setEliminato(rs.getBoolean("eliminato")); // ← aggiunto
                    p.setImmagine(rs.getString("immagine"));


                    return p;
                }
            }
        }
        return null;
    }

    public void update(Prodotto prodotto) throws SQLException {
    	String sql = "UPDATE prodotto SET nome = ?, descrizione = ?, prezzo = ?, disponibilita = ?, immagine = ? WHERE id = ?";
;
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, prodotto.getNome());
            ps.setString(2, prodotto.getDescrizione());
            ps.setDouble(3, prodotto.getPrezzo());
            ps.setInt(4, prodotto.getDisponibilita());
            ps.setInt(5, prodotto.getId());
            ps.setString(5, prodotto.getImmagine());
            ps.setInt(6, prodotto.getId());

            ps.executeUpdate();
        }
    }

    public void softDelete(int id) throws SQLException {
        String sql = "UPDATE prodotto SET eliminato = TRUE WHERE id = ?";
        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }


}