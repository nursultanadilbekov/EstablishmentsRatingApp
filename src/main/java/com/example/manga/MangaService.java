package com.example.manga;

import java.sql.*;
import java.util.*;

public class MangaService {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/manga_db";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "your_password";

    // Establish database connection
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    // Fetch manga list
    public List<Manga> getMangaList() {
        List<Manga> mangaList = new ArrayList<>();
        String query = "SELECT * FROM Manga";
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                mangaList.add(new Manga(rs.getInt("id"), rs.getString("title"), rs.getString("genre"), rs.getString("description")));
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
