package com.example.manga;

import com.example.manga.Manga;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MangaService {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/manga";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "1234";

    private static Connection connect() throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        if (connection == null || !connection.isValid(5)) {
            throw new SQLException("Failed to establish a valid database connection.");
        }
        return connection;
    }

    public List<Manga> getMangaList() {
        List<Manga> mangaList = new ArrayList<>();
        String query = "SELECT * FROM Manga";
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                mangaList.add(new Manga(
                        rs.getInt("mangaid"),
                        rs.getString("title"),
                        rs.getString("genre"),
                        rs.getString("status"),
                        rs.getString("author"),
                        rs.getDouble("rating"),
                        rs.getString("thumbnailurl")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mangaList;
    }

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