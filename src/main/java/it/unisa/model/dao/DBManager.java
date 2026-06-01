package it.unisa.model.dao;

import java.sql.*;

public class DBManager {
    private static final String URL = "jdbc:mysql://localhost:3307/SUNSET_BNB";
    private static final String USER = "root";
    private static final String PASSWORD = "060804FedeFrancy03052011!";

    public static Connection getConnection() throws SQLException {
        try {
            // Registra il driver JDBC MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL non trovato!", e);
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
