package it.unisa.model.dao;

import java.sql.*;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import it.unisa.model.UtenteGoogle;

public class UtenteGoogleDAO {

    private static final String DB_URL = "jdbc:mysql://localhost:3307/SUNSET_BNB";
    private static final String DB_USER = "root"; // o il tuo user
    private static final String DB_PASSWORD = "060804FedeFrancy03052011!"; // cambia con la tua password

    protected Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Importante per MySQL
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC non trovato", e);
        }
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
    
    
    public boolean utenteEsiste(String email) throws SQLException {
        String sql = "SELECT 1 FROM Utente_google WHERE email = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // ritorna true se trova almeno una riga
        }
    }


    public void salvaUtenteGoogle(String nome, String email, String immagineProfilo) throws SQLException {
        String sql = "INSERT INTO Utente_google (nome, email, immagine_profilo, tipo_autenticazione) VALUES (?, ?, ?, 'GOOGLE')";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nome);
            ps.setString(2, email);
            ps.setString(3, immagineProfilo);

            ps.executeUpdate();
        }
    }

    public UtenteGoogle getUtenteByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM Utente_google WHERE email = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                UtenteGoogle u = new UtenteGoogle();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setEmail(rs.getString("email"));
                u.setImmagineProfilo(rs.getString("immagine_profilo"));
                return u;
            }
        }
        return null;
    }

    
}
