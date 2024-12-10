package com.example.manga;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MangaDAO {
    private static final String URL = "jdbc:postgresql://localhost:5432/manga_library";
    private static final String USER = "postgres";
    private static final String PASSWORD = "your_password";

    public List<String> getAllMangaTitles() throws SQLException {
        List<String> titles = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT title FROM manga")) {

            while (resultSet.next()) {
                titles.add(resultSet.getString("title"));
            }
        }
        return titles;
    }

    public void addManga(String title, String author, String genre, Date releaseDate, int volumes) throws SQLException {
        String sql = "INSERT INTO manga (title, author, genre, release_date, volumes) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, author);
            preparedStatement.setString(3, genre);
            preparedStatement.setDate(4, releaseDate);
            preparedStatement.setInt(5, volumes);

            preparedStatement.executeUpdate();
        }
    }
}
