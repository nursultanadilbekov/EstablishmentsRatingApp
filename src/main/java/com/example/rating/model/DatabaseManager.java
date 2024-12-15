package com.example.rating;

import java.sql.*;
import java.util.*;
import javax.swing.*;

public class Database {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/ratingapp";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "1234";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public void addEstablishment(String name, String type) {
        String query = "INSERT INTO establishments (name, type) VALUES (?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, type);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getEstablishments() {
        List<String> establishments = new ArrayList<>();
        String query = "SELECT name FROM establishments";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                establishments.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return establishments;
    }

    public void addRating(String establishment, int rating) {
        String query = "INSERT INTO ratings (establishment_name, rating) VALUES (?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, establishment);
            stmt.setInt(2, rating);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double getAverageRating(String establishment) {
        double avgRating = 0;
        String query = "SELECT AVG(rating) FROM ratings WHERE establishment_name = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, establishment);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                avgRating = rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return avgRating;
    }
}
