package com.example.rating.controller;

import com.example.rating.model.DatabaseManager;
import com.example.rating.model.Favourite;
import com.example.rating.model.Establishment;
import com.example.rating.view.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class EstablishmentController  {
    private static final Logger log = LoggerFactory.getLogger(EstablishmentController.class);
    private final TopEstablishmentsView topEstablishmentsView;
    private AddEstablishmentView addEstablishmentView;
    private ViewEstablishmentsView viewEstablishmentsView;
    private DatabaseManager databaseManager;
    private Favourite favourite;
    private Establishment establishment;
//    private JPanel addEstablishmentView;
//    private JPanel topEstablishmentsView;
//    private JPanel viewEstablishmentsView;

    public EstablishmentController() throws SQLException {
        this.addEstablishmentView = new AddEstablishmentView();
        this.viewEstablishmentsView = new ViewEstablishmentsView(this);
        this.topEstablishmentsView = new TopEstablishmentsView(this);
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

//    // Like an establishment
//    public void likeEstablishment(int id) {
//        try {
//            databaseManager.likeEstablishment(id);
//            updateTopEstablishments();
//        } catch (SQLException e) {
//            handleDatabaseError(e, "Error liking establishment.");
//        }
//    }
//
//    // Dislike an establishment
//    public void dislikeEstablishment(int id) {
//        try {
//            databaseManager.dislikeEstablishment(id);
//            updateTopEstablishments();
//        } catch (SQLException e) {
//            handleDatabaseError(e, "Error disliking establishment.");
//        }
//    }

    // Handle database errors and show error messages

//    public JPanel getAddEstablishmentView() {
//        if (addEstablishmentView == null) {
//            addEstablishmentView = new AddEstablishmentView(this); // ваш класс представления
//        }
//        return addEstablishmentView;
//    }
//
//    public JPanel getTopEstablishmentsView() {
//        if (topEstablishmentsView == null) {
//            topEstablishmentsView = new TopEstablishmentsView(this); // ваш класс представления
//        }
//        return topEstablishmentsView;
//    }
//
//    public JPanel getViewEstablishmentsView() {
//        if (viewEstablishmentsView == null) {
//            viewEstablishmentsView = new ViewEstablishmentsView(this); // ваш класс представления
//        }
//        return viewEstablishmentsView;
//    }


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

    public void updateTopEstablishments() {
        try {
            List<Establishment> establishments = databaseManager.getTopEstablishments();
            topEstablishmentsView.setEstablishments(establishments);  // Update the view with new data
        } catch (SQLException e) {
            log.error("Failed to fetch top establishments", e);
            JOptionPane.showMessageDialog(null, "Error fetching top establishments.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    // Update the all establishments list in the view
    public void updateViewEstablishments() {
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
    public void addLike(int establishmentId) {
        try {
            databaseManager.likeEstablishment(establishmentId);
        } catch (SQLException e) {
            handleDatabaseError(e, "Error liking establishment.");
        }
    }

    public void addDislike(int establishmentId) {
        try {
            databaseManager.dislikeEstablishment(establishmentId);
        } catch (SQLException e) {
            handleDatabaseError(e, "Error disliking establishment.");
        }
    }

    private void updateEstablishmentCounts(int id, JLabel likeCountLabel, JLabel dislikeCountLabel) {
        try {
            int likes = databaseManager.getLikesCount(id);
            int dislikes = databaseManager.getDislikesCount(id);
            likeCountLabel.setText("Likes: " + likes);
            dislikeCountLabel.setText("Dislikes: " + dislikes);
        } catch (SQLException e) {
            handleDatabaseError(e, "Error fetching establishment counts.");
        }
    }

    private void disableButtons(JButton likeButton, JButton dislikeButton) {
        likeButton.setEnabled(false);
        dislikeButton.setEnabled(false);
    }
    public void updateLikeDislikeState(Establishment establishment, JButton likeButton, JButton dislikeButton, JLabel likeCountLabel, JLabel dislikeCountLabel) {
        // Обновление текста на кнопках и счетчиков
        likeCountLabel.setText("Likes: " + establishment.getLikesCount());
        dislikeCountLabel.setText("Dislikes: " + establishment.getDislikesCount());

        // Можно добавить условие, чтобы кнопки лайков/дизлайков не были активны, если уже был поставлен лайк или дизлайк
        likeButton.setEnabled(establishment.getLikesCount() == 0); // Пример проверки
        dislikeButton.setEnabled(establishment.getDislikesCount() == 0); // Пример проверки
    }
    public void removeLike(int establishmentId) throws SQLException {
        databaseManager.decreaseLikesInDatabase(establishmentId);
    }

    public void removeDislike(int establishmentId) throws SQLException {
       databaseManager.decreaseDislikesInDatabase(establishmentId);
    }
    // Method to retrieve filtered establishments based on search query and category
    public List<Establishment> getFilteredEstablishments(String searchQuery, String selectedCategory) throws SQLException {
        return databaseManager.getFilteredEstablishments(searchQuery, selectedCategory);
    }
    public List<Establishment> getAllEstablishments() throws SQLException {
     return databaseManager.getAllEstablishments();
    }
    public void addFavourite(int id){
     databaseManager.addFavourite(id);
    }

    public void removeFavourite(int id){
     databaseManager.removeFavourite(id);
    }
    public void updateRating(int userId, int establishmentId, boolean isLiked, boolean isDisliked) throws SQLException {
        databaseManager.updateRating(userId, establishmentId, isLiked, isDisliked);
    }
}
