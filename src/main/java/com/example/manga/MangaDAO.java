package com.example.manga;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MangaDAO {

    // Add a new manga record to the database
    public void addManga(String title, String genre, double price, int stock, double rating) {
        String query = "INSERT INTO Manga (title, genre, price, stock, rating) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, genre);
            preparedStatement.setDouble(3, price);
            preparedStatement.setInt(4, stock);
            preparedStatement.setDouble(5, rating);
            preparedStatement.executeUpdate();

            System.out.println("Manga added successfully!");
        } catch (SQLException e) {
            System.err.println("Error adding manga: " + e.getMessage());
        }
    }

    // List all manga records in the database
    public void listManga() {
        String query = "SELECT * FROM Manga";
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id") +
                        ", Title: " + resultSet.getString("title") +
                        ", Genre: " + resultSet.getString("genre") +
                        ", Price: " + resultSet.getDouble("price") +
                        ", Stock: " + resultSet.getInt("stock") +
                        ", Rating: " + resultSet.getDouble("rating"));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving manga: " + e.getMessage());
        }
    }

    // Fetch all manga records from the database and return as a list
    public List<Manga> getAllManga() {
        List<Manga> mangaList = new ArrayList<>();
        String query = "SELECT title, genre, price, stock, rating FROM Manga";

        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String genre = resultSet.getString("genre");
                double price = resultSet.getDouble("price");
                int stock = resultSet.getInt("stock");
                double rating = resultSet.getDouble("rating");

                // Create Manga object and add it to the list
                Manga manga = new Manga(title, genre, price, stock, rating);
                mangaList.add(manga);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching manga list: " + e.getMessage());
        }

        return mangaList;
    }
}
