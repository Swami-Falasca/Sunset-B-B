package it.unisa.model.dao;

import it.unisa.model.Admin;

import java.sql.*;


public class AdminDAO {

    public Admin login(String username, String password) throws SQLException {
        Admin admin = null;

        try (Connection con = DBManager.getConnection()) {
        	String sql = "SELECT id, username, password_hash, nome, gender FROM admin WHERE username = ? AND password_hash = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password); // In produzione usa hash sicuro

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                admin = new Admin();
                admin.setId(rs.getInt("id"));
                admin.setUsername(rs.getString("username"));
                admin.setPasswordHash(rs.getString("password_hash"));
                admin.setNome(rs.getString("nome"));          
                admin.setGender(rs.getString("gender"));
            }
        }

        return admin;
    }
    
    public Admin loginByUsername(String username, String hashedPassword) throws SQLException {
        String sql = "SELECT id, username, password_hash, nome, gender FROM admin WHERE username = ? AND password_hash = ?";
        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, hashedPassword);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Admin admin = new Admin();
                admin.setId(rs.getInt("id"));
                admin.setUsername(rs.getString("username"));
                admin.setPasswordHash(rs.getString("password_hash"));
                admin.setNome(rs.getString("nome"));
                admin.setGender(rs.getString("gender"));
                return admin;
            }
        }
        return null;
    }
    
    public Admin loginByEmail(String email, String hashedPassword) throws SQLException {
        String sql = "SELECT id, username, password_hash, nome, gender FROM admin WHERE email = ? AND password_hash = ?";
        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, hashedPassword);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Admin admin = new Admin();
                admin.setId(rs.getInt("id"));
                admin.setUsername(rs.getString("username"));
                admin.setPasswordHash(rs.getString("password_hash"));
                admin.setNome(rs.getString("nome"));
                admin.setGender(rs.getString("gender"));
                return admin;
            }
        }
        return null;
    }
}
