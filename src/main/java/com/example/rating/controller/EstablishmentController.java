package com.example.rating.controller;

import com.example.rating.model.DatabaseManager;
import com.example.rating.model.Establishment;
import com.example.rating.view.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class EstablishmentController {
    private static final Logger log = LoggerFactory.getLogger(EstablishmentController.class);
    private AddEstablishmentView addEstablishmentView;
    private TopEstablishmentsView topEstablishmentsView;
    private ViewEstablishmentsView viewEstablishmentsView;
    private DatabaseManager databaseManager;
    private Establishment establishment;

    public EstablishmentController() throws SQLException {
        this.addEstablishmentView = new AddEstablishmentView();
        this.topEstablishmentsView = new TopEstablishmentsView();
        this.viewEstablishmentsView = new ViewEstablishmentsView();
        this.databaseManager = new DatabaseManager();

        // Add listener for the Add Establishment button
        addEstablishmentView.addSubmitButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddEstablishment();
                addEstablishmentView.clearFormFields();
            }
        });

    }

//    // Handle button clicks and navigate accordingly
//    public void handleButtonClick(String action) {
//        switch (action) {
//            case "AddEstablishment":
//                getAddEstablishmentView();
//                break;
//            case "TopEstablishments":
//                updateTopEstablishments();
//                getTopEstablishmentsView();
//                break;
//            case "ViewEstablishments":
//                updateViewEstablishments();
//                getViewEstablishmentsView();
//                break;
//            default:
//                JOptionPane.showMessageDialog(null, "Unknown action: " + action);
//        }
//    }

    // Handle the Add Establishment button click
    public void handleAddEstablishment() {
        String name = addEstablishmentView.getName();
        String address = addEstablishmentView.getAddress();
        String description = addEstablishmentView.getDescription();

        // Convert category to appropriate ID (you might want to map category string to an ID)
        int categoryId = addEstablishmentView.getCategoryId();

        // Assuming user ID is passed or stored somewhere
        int userId = 1;

        // Call the model's addEstablishment method
        try {
            databaseManager.addEstablishment(userId, name, address, description, addEstablishmentView.getCategory());
            JOptionPane.showMessageDialog(null, "Establishment added successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error adding establishment.");
        }
    }

    private int getCategoryIdByName(String category) {
        // Implement the logic to get category ID by its name
        return 1;  // Placeholder for category ID
    }

    // Like an establishment
    public void likeEstablishment(int id) {
        try {
            databaseManager.likeEstablishment(id);
            updateTopEstablishments();
        } catch (SQLException e) {
            handleDatabaseError(e, "Error liking establishment.");
        }
    }

    // Dislike an establishment
    public void dislikeEstablishment(int id) {
        try {
            databaseManager.dislikeEstablishment(id);
            updateTopEstablishments();
        } catch (SQLException e) {
            handleDatabaseError(e, "Error disliking establishment.");
        }
    }

    // Handle database errors and show error messages
    private void handleDatabaseError(SQLException e, String message) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, message);
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

    public JPanel getAddEstablishmentView() {
        return addEstablishmentView.getView();
    }

    public JPanel getTopEstablishmentsView() {
        updateTopEstablishments();
        return topEstablishmentsView.getView();
    }

    public JPanel getViewEstablishmentsView() {
        updateViewEstablishments();
        return viewEstablishmentsView.getView();
    }


    // Update the top establishments list in the view
    private void updateTopEstablishments() {
        try {
            List<Establishment> establishments = databaseManager.getTopEstablishments();
            topEstablishmentsView.updateList(establishments);
        } catch (SQLException e) {
            log.error("Failed to fetch top establishments", e);
            JOptionPane.showMessageDialog(null, "Error fetching top establishments.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Update the all establishments list in the view
    private void updateViewEstablishments() {
        try {
            List<Establishment> establishments = databaseManager.getAllEstablishments();
            viewEstablishmentsView.updateList(establishments);
        } catch (SQLException e) {
            log.error("Failed to fetch all establishments", e);
            JOptionPane.showMessageDialog(null, "Error fetching establishments.", "Error", JOptionPane.ERROR_MESSAGE);
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
