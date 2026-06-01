package it.unisa.model.dao;

import java.sql.*;


import it.unisa.model.Utente;
import it.unisa.util.PasswordUtils;

public class UtenteDAO {

    // 🔐 Registrazione nuovo utente con password hashata
    public boolean registraUtente(Utente utente) throws SQLException {
        String sql = "INSERT INTO utenti (nome, cognome, data_nascita, tipo_documento, numero_documento, nome_file, residenza, email, password) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, utente.getNome());
            ps.setString(2, utente.getCognome());
            ps.setDate(3, utente.getDataNascita());
            ps.setString(4, utente.getTipoDocumento());
            ps.setString(5, utente.getNumeroDocumento());
            ps.setString(6, utente.getNome_file());
            ps.setString(7, utente.getResidenza());
            ps.setString(8, utente.getEmail());

            // Hash della password prima di salvarla
            ps.setString(9, utente.getPassword()); // già hashata dalla servlet

            return ps.executeUpdate() > 0;
        }
    }

    // 🔍 Trova utente tramite email
    public Utente findByEmail(String email) {
        String sql = "SELECT * FROM utenti WHERE email = ?";
        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Utente u = new Utente();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setCognome(rs.getString("cognome"));
                u.setDataNascita(rs.getDate("data_nascita"));
                u.setTipoDocumento(rs.getString("tipo_documento"));
                u.setNumeroDocumento(rs.getString("numero_documento"));
                u.setNome_file(rs.getString("nome_file"));
                u.setResidenza(rs.getString("residenza"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password")); // password hashata
                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 🔑 Login con confronto tra password hashata e quella salvata
    public Utente login(String email, String plainPassword) {
        Utente utente = findByEmail(email);
        if (utente != null) {
            String hashedInput = PasswordUtils.hashPassword(plainPassword);
            if (hashedInput.equals(utente.getPassword())) {
                return utente;
            }
        }
        return null;
    }
    
  

}
