package com.example.rating.model;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

public class DatabaseManager {
    private Connection connection;

    public DatabaseManager() throws SQLException {
        // Establish database connection (use your actual connection details)
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/manga", "postgres", "1234");
    }

    // Method to validate user login credentials (username and password)
    public boolean validateUserLogin(String username, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Fetch the hashed password from the database and compare it with the entered password
                    String storedHashedPassword = rs.getString("password");
                    return BCrypt.checkpw(password, storedHashedPassword); // BCrypt comparison
                }
            }
        }
        return false;
    }

    // Method to register a new user (with hashed password)
    public boolean registerUser(String username, String password) throws SQLException {
        // Check if username already exists
        if (isUsernameTaken(username)) {
            return false; // Username is already taken
        }

        // Hash the password before storing
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        // Insert the user data into the database
        String query = "INSERT INTO users (username, password, created_at, updated_at) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);
            stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            stmt.executeUpdate();
            return true; // Successfully registered
        }
    }

    // Helper method to check if a username already exists
    private boolean isUsernameTaken(String username) throws SQLException {
        String query = "SELECT 1 FROM users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // If a record exists, the username is taken
            }
        }
    }

    public void addEstablishment(int userId, String name, String address, String description, String categoryName) throws SQLException {
        // SQL queries
        String insertCategoryQuery = "INSERT INTO categories (name) VALUES (?) ON CONFLICT (name) DO NOTHING RETURNING id";
        String selectCategoryQuery = "SELECT id FROM categories WHERE name = ?";
        String insertEstablishmentQuery = "INSERT INTO establishments (user_id, name, address, description, category_id, likes, dislikes, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, ?, 0, 0, ?, ?)";

        try (PreparedStatement insertCategoryStmt = connection.prepareStatement(insertCategoryQuery);
             PreparedStatement selectCategoryStmt = connection.prepareStatement(selectCategoryQuery);
             PreparedStatement insertEstablishmentStmt = connection.prepareStatement(insertEstablishmentQuery)) {

            // Step 1: Ensure the category exists
            int categoryId = -1;
            // Try to insert the category (if it doesn't already exist)
            insertCategoryStmt.setString(1, categoryName);
            ResultSet insertCategoryResult = insertCategoryStmt.executeQuery();
            if (insertCategoryResult.next()) {
                categoryId = insertCategoryResult.getInt("id"); // Get the ID of the newly inserted category
            } else {
                // Category already exists, fetch its ID
                selectCategoryStmt.setString(1, categoryName);
                ResultSet selectCategoryResult = selectCategoryStmt.executeQuery();
                if (selectCategoryResult.next()) {
                    categoryId = selectCategoryResult.getInt("id");
                }
            }

            // Step 2: Add the establishment
            if (categoryId != -1) {
                insertEstablishmentStmt.setInt(1, userId);
                insertEstablishmentStmt.setString(2, name);
                insertEstablishmentStmt.setString(3, address);
                insertEstablishmentStmt.setString(4, description);
                insertEstablishmentStmt.setInt(5, categoryId);
                insertEstablishmentStmt.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
                insertEstablishmentStmt.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
                insertEstablishmentStmt.executeUpdate();
            } else {
                throw new SQLException("Failed to retrieve or create category.");
            }
        }
    }


    // Fetch top establishments with updated fields
    public List<Establishment> getTopEstablishments() throws SQLException {
        String query = "SELECT * FROM establishments ORDER BY likes DESC LIMIT 10";
        try (PreparedStatement stmt = connection.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            List<Establishment> establishments = new ArrayList<>();
            while (rs.next()) {
                establishments.add(new Establishment(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("description"),
                        rs.getInt("likes"),
                        rs.getInt("dislikes"),
                        rs.getInt("category_id"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()
                ));
            }
            return establishments;
        }
    }

    public List<Establishment> getAllEstablishments() throws SQLException {
        String query = "SELECT * FROM establishments ORDER BY created_at DESC";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                List<Establishment> establishments = new ArrayList<>();
                while (rs.next()) {
                    establishments.add(new Establishment(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getString("name"),
                            rs.getString("address"),
                            rs.getString("description"),
                            rs.getInt("likes"),
                            rs.getInt("dislikes"),
                            rs.getInt("category_id"),
                            rs.getTimestamp("created_at").toLocalDateTime(),
                            rs.getTimestamp("updated_at").toLocalDateTime()
                    ));
                }
                return establishments;
            }
        }
    }


    // Like an establishment
    public void likeEstablishment(int id) throws SQLException {
        updateLikesOrDislikes(id, "likes");
    }

    // Dislike an establishment
    public void dislikeEstablishment(int id) throws SQLException {
        updateLikesOrDislikes(id, "dislikes");
    }

    // Helper method to increment likes or dislikes and update the `updated_at` field
    private void updateLikesOrDislikes(int id, String column) throws SQLException {
        String query = "UPDATE establishments SET " + column + " = " + column + " + 1, updated_at = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }

    // Method to fetch an establishment by ID (useful for testing and debugging)
    public Establishment getEstablishmentById(int id) throws SQLException {
        String query = "SELECT * FROM establishments WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Establishment(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getString("name"),
                            rs.getString("address"),
                            rs.getString("description"),
                            rs.getInt("likes"),
                            rs.getInt("dislikes"),
                            rs.getInt("category_id"),
                            rs.getTimestamp("created_at").toLocalDateTime(),
                            rs.getTimestamp("updated_at").toLocalDateTime()
                    );
                } else {
                    throw new SQLException("Establishment not found for ID: " + id);
                }
            }
        }
    }
    // Method to get user ID by username
    public int getUserId(String username) {
        int userId = -1;  // Default value for invalid user ID

        String query = "SELECT user_id FROM users WHERE username = ?"; // SQL query to fetch user ID
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Set the username parameter
            stmt.setString(1, username);

            // Execute query and get result set
            ResultSet rs = stmt.executeQuery();

            // Check if a user with the given username exists
            if (rs.next()) {
                // Retrieve the user ID from the result set
                userId = rs.getInt("user_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userId;
    }
}
