package com.example.rating.controller;

import com.example.rating.model.DatabaseManager;
import com.example.rating.model.Establishment;
import com.example.rating.view.EstablishmentView;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class EstablishmentController {
    private EstablishmentView establishmentView;
    private DatabaseManager databaseManager;

    public EstablishmentController() throws SQLException {
        this.establishmentView = new EstablishmentView();
        this.databaseManager = new DatabaseManager(); // Database manager instance
    }

    public JPanel getView() {
        return establishmentView.getView();
    }

    // Method to handle button clicks and navigate accordingly
    public void handleButtonClick(String action) {
        switch (action) {
            case "AddEstablishment":
                showAddEstablishmentView();
                break;
            case "TopEstablishments":
                showTopEstablishmentsView();
                break;
            case "ViewEstablishments":
                showViewEstablishmentsView();
                break;
            default:
                JOptionPane.showMessageDialog(null, "Unknown action: " + action);
        }
    }

    // Show Add Establishment View
    public void showAddEstablishmentView() {
        establishmentView.showView("AddEstablishment");
    }

    // Show Top Establishments View
    public void showTopEstablishmentsView() {
        try {
            List<Establishment> topEstablishments = databaseManager.getTopEstablishments();
            establishmentView.showTopEstablishments(topEstablishments);
        } catch (SQLException e) {
            handleDatabaseError(e, "Error fetching top establishments.");
        }
    }

    // Show All Establishments
    public void showViewEstablishmentsView() {
        try {
            List<Establishment> establishments = databaseManager.getTopEstablishments();  // Or use another method if needed
            establishmentView.showEstablishments(establishments);
        } catch (SQLException e) {
            handleDatabaseError(e, "Error fetching establishments.");
        }
    }

    // Add a new establishment
    public void addNewEstablishment(String name, String address, String description, String category) {
        try {
            databaseManager.addEstablishment(name, address, description, category);
            JOptionPane.showMessageDialog(null, "Establishment added successfully.");
            refreshTopEstablishmentsView();
        } catch (SQLException e) {
            handleDatabaseError(e, "Error adding establishment.");
        }
    }

    // Like an establishment
    public void likeEstablishment(int id) {
        updateEstablishment(id, "liked");
    }

    // Dislike an establishment
    public void dislikeEstablishment(int id) {
        updateEstablishment(id, "disliked");
    }

    // Helper method to handle liking and disliking establishments
    private void updateEstablishment(int id, String action) {
        try {
            if (action.equals("liked")) {
                databaseManager.likeEstablishment(id);
            } else if (action.equals("disliked")) {
                databaseManager.dislikeEstablishment(id);
            }
            JOptionPane.showMessageDialog(null, "Establishment " + action + " successfully.");
            refreshTopEstablishmentsView();
        } catch (SQLException e) {
            handleDatabaseError(e, "Error " + action + " establishment.");
        }
    }

    // Handle database errors and show error messages
    private void handleDatabaseError(SQLException e, String message) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, message);
    }

    // Refresh the top establishments view after any update
    private void refreshTopEstablishmentsView() {
        showTopEstablishmentsView(); // This refreshes the view by fetching the latest data
    }

    // Method to validate user login
    public boolean validateLogin(String username, String password) {
        try {
            return databaseManager.validateUserLogin(username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to register a new user
    public boolean registerUser(String username, String password) {
        try {
            return databaseManager.registerUser(username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
