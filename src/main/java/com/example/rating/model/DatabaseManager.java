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
    // Helper method to increment likes or dislikes and update the `updated_at` field
    private void updateLikesOrDislikes(int id, String column) throws SQLException {
        String query = "UPDATE establishments SET " + column + " = " + column + " + 1, updated_at = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }
    public void likeEstablishment(int establishmentId) throws SQLException {
        String query = "UPDATE establishments SET likes = likes + 1 WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, establishmentId);
            stmt.executeUpdate();
        }
    }

    public void dislikeEstablishment(int establishmentId) throws SQLException {
        String query = "UPDATE establishments SET dislikes = dislikes + 1 WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, establishmentId);
            stmt.executeUpdate();
        }
    }

    public int getLikesCount(int establishmentId) throws SQLException {
        String query = "SELECT likes FROM establishments WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, establishmentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("likes");
                }
            }
        }
        return 0;
    }

    public int getDislikesCount(int establishmentId) throws SQLException {
        String query = "SELECT dislikes FROM establishments WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, establishmentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("dislikes");
                }
            }
        }
        return 0;
    }
    // Decrease likes count for the establishment in the database
    public void decreaseLikesInDatabase(int establishmentId) throws SQLException {
        String query = "UPDATE establishments SET likes = likes - 1 WHERE id = ?";

        // Execute the query to decrease the likes count by 1
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, establishmentId);
            stmt.executeUpdate();
        }
    }

    // Decrease dislikes count for the establishment in the database
    public void decreaseDislikesInDatabase(int establishmentId) throws SQLException {
        String query = "UPDATE establishments SET dislikes = dislikes - 1 WHERE id = ?";

        // Execute the query to decrease the dislikes count by 1
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, establishmentId);
            stmt.executeUpdate();
        }
    }

    // Helper method to get the updated likes count from the database
    public int getUpdatedLikeCountFromDatabase(int establishmentId) throws SQLException {
        String query = "SELECT likes FROM establishments WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, establishmentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("likes");
                }
            }
        }
        return 0;  // If no establishment was found, return 0
    }

    // Helper method to get the updated dislikes count from the database
    public int getUpdatedDislikeCountFromDatabase(int establishmentId) throws SQLException {
        String query = "SELECT dislikes FROM establishments WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, establishmentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("dislikes");
                }
            }
        }
        return 0;  // If no establishment was found, return 0
    }
    public List<Establishment> getFilteredEstablishments(String searchQuery, String category) throws SQLException {
        // SQL query to filter establishments by name and category
        String sql = "SELECT * FROM establishments WHERE name ILIKE ? AND category_id IN (SELECT id FROM categories WHERE name ILIKE ?) ORDER BY created_at DESC";

        // Prepare the statement with the provided query
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Set the search parameters for name and category
            stmt.setString(1, "%" + searchQuery + "%");
            stmt.setString(2, "%" + category + "%");

            // Execute the query and process the result
            try (ResultSet rs = stmt.executeQuery()) {
                List<Establishment> establishments = new ArrayList<>();
                while (rs.next()) {
                    // Create an Establishment object for each row in the result
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
        } catch (SQLException e) {
            // Handle any SQL exceptions
            System.err.println("SQL Error: " + e.getMessage());
            throw e;
        }
    }
    public boolean addFavourite(Favourite favourite) {
        String sql = "INSERT INTO favourites (user_id, establishment_id) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, favourite.getUserId());
            stmt.setInt(2, favourite.getEstablishmentId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
