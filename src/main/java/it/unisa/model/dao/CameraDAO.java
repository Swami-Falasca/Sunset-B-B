package it.unisa.model.dao;
import it.unisa.model.Camera;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CameraDAO {

    public List<Camera> findAll() throws SQLException {
        List<Camera> list = new ArrayList<>();
        String sql = "SELECT c.*, ci.nome AS nome_citta FROM camera c " +
                     "JOIN citta ci ON c.id_citta = ci.id " +
                     "WHERE c.eliminato = 0 " +
                     "ORDER BY c.id ASC";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    public Camera findById(int id) throws SQLException {
        String sql = "SELECT c.*, ci.nome AS nome_citta FROM camera c " +
                     "JOIN citta ci ON c.id_citta = ci.id WHERE c.id = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        }
        return null;
    }

    public void update(Camera c) throws SQLException {
        String sql = "UPDATE camera SET nome=?, descrizione=?, extra=?, prezzo=? WHERE id=?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getNome());
            ps.setString(2, c.getDescrizione());
            ps.setString(3, c.getExtra());
            ps.setDouble(4, c.getPrezzo());
            ps.setInt(5, c.getId());
            ps.executeUpdate();
        }
    }

    /**
     * Inserisce un nuovo B&B legato a una città specifica.
     */
    public void insert(Camera c) throws SQLException {
        String sql = "INSERT INTO camera (nome, descrizione, extra, prezzo, immagini, id_citta, eliminato) " +
                     "VALUES (?, ?, ?, ?, ?, ?, 0)";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getNome());
            ps.setString(2, c.getDescrizione());
            ps.setString(3, c.getExtra());
            ps.setDouble(4, c.getPrezzo());
            ps.setString(5, c.getImmagini());
            ps.setInt(6, c.getIdCitta());
            ps.executeUpdate();
        }
    }

    /**
     * Eliminazione "soft": marca la camera come eliminata invece di
     * cancellarla fisicamente, per non rompere prenotazioni/recensioni
     * già collegate al suo id. Sparisce sia dalla lista admin sia dalla
     * ricerca per città lato utente perché entrambe filtrano eliminato = 0.
     */
    public void delete(int id) throws SQLException {
        String sql = "UPDATE camera SET eliminato = 1 WHERE id = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<Camera> findByCitta(String nomeCitta) throws SQLException {
        List<Camera> list = new ArrayList<>();
        String sql = "SELECT c.*, ci.nome AS nome_citta FROM camera c " +
                     "JOIN citta ci ON c.id_citta = ci.id " +
                     "WHERE ci.nome = ? AND c.eliminato = 0 " +
                     "ORDER BY c.nome";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nomeCitta);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    private Camera mapRow(ResultSet rs) throws SQLException {
        Camera c = new Camera();
        c.setId(rs.getInt("id"));
        c.setNome(rs.getString("nome"));
        c.setDescrizione(rs.getString("descrizione"));
        c.setExtra(rs.getString("extra"));
        c.setPrezzo(rs.getDouble("prezzo"));
        c.setImmagini(rs.getString("immagini"));
        c.setIdCitta(rs.getInt("id_citta"));
        c.setNomeCitta(rs.getString("nome_citta"));
        return c;
    }
}
