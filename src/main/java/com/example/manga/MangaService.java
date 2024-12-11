package com.example.manga;

import java.sql.*;
import java.util.*;

public class MangaService {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/manga";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "1234";

    // Establish database connection without using connection pool
    private static Connection connect() throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        if (connection == null || !connection.isValid(5)) { // Check connection validity with a timeout of 5 seconds
            throw new SQLException("Failed to establish a valid database connection.");
        }
        return connection;
    }

    // Fetch manga list
    public List<Manga> getMangaList() {
        List<Manga> mangaList = new ArrayList<>();
        String query = "SELECT * FROM Manga";
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                mangaList.add(new Manga(rs.getInt("mangaid"), rs.getString("title"), rs.getString("genre"), rs.getString("status")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mangaList;
    }

    // Fetch chapters for a specific manga
    public List<String> getChaptersForManga(int mangaId) {
        List<String> chapters = new ArrayList<>();
        String query = "SELECT title FROM Chapter WHERE manga_id = ?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, mangaId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                chapters.add(rs.getString("title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chapters;
    }
}
