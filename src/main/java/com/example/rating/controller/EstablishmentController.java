package com.example.rating.controller;

import com.example.rating.model.DatabaseManager;
import com.example.rating.model.Establishment;
import com.example.rating.view.AddEstablishmentView;
import com.example.rating.view.TopEstablishmentsView;
import com.example.rating.view.ViewEstablishmentsView;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class EstablishmentController {
    private AddEstablishmentView addEstablishmentView;
    private TopEstablishmentsView topEstablishmentsView;
    private ViewEstablishmentsView viewEstablishmentsView;
    private DatabaseManager databaseManager;

    public EstablishmentController() throws SQLException {
        this.addEstablishmentView = new AddEstablishmentView();
        this.topEstablishmentsView = new TopEstablishmentsView();
        this.viewEstablishmentsView = new ViewEstablishmentsView();
        this.databaseManager = new DatabaseManager();
    }


    // Method to handle button clicks and navigate accordingly

    public void handleButtonClick(String action) {
        switch (action) {
            case "AddEstablishment":
                getAddEstablishmentView();
                updateAllEstablishments();
                updateTopEstablishments();
                break;
            case "TopEstablishments":
                updateAllEstablishments();
                updateTopEstablishments();
                getTopEstablishmentsView();
                break;
            case "ViewEstablishments":
                updateAllEstablishments();
                updateTopEstablishments();
                getViewEstablishmentsView();
                break;
            default:
                JOptionPane.showMessageDialog(null, "Unknown action: " + action);
        }
    }
//    // Add a new establishment
//    public void addNewEstablishment(String name, String address, String description, String category) {
//        try {
//            databaseManager.addEstablishment(name, address, description, category);
//            JOptionPane.showMessageDialog(null, "Establishment added successfully.");
//            refreshTopEstablishmentsView();
//        } catch (SQLException e) {
//            handleDatabaseError(e, "Error adding establishment.");
//        }
//    }
    // Like an establishment

//    public void likeEstablishment(int id) {
//        updateEstablishment(id, "liked");
//    }
//    // Dislike an establishment
//
//    public void dislikeEstablishment(int id) {
//        updateTopEstablishments(id, "disliked");
//    }

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
        System.out.println("add");
        return addEstablishmentView.getView();
    }

    public JPanel getTopEstablishmentsView() {
        System.out.println("top");
        return topEstablishmentsView.getView();
    }

    public JPanel getViewEstablishmentsView() {
        System.out.println("view");
        return viewEstablishmentsView.getView();
    }

    public void updateTopEstablishments() {
        try {
            List<Establishment> establishments = databaseManager.getTopEstablishments();
            topEstablishmentsView.updateList(establishments);
        } catch (SQLException e) {
            handleDatabaseError(e, "Error fetching top establishments.");
        }
    }

    public void updateAllEstablishments() {
        try {
            List<Establishment> establishments = databaseManager.getAllEstablishments();
            viewEstablishmentsView.updateList(establishments);
        } catch (SQLException e) {
            handleDatabaseError(e, "Error fetching establishments.");
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
