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

public class EstablishmentController {
    private static final Logger log = LoggerFactory.getLogger(EstablishmentController.class);
    private AddEstablishmentView addEstablishmentView;
    private TopEstablishmentsView topEstablishmentsView;
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
    public void likeEstablishment(int id, JButton likeButton, JButton dislikeButton, JLabel likeCountLabel, JLabel dislikeCountLabel) {
        try {
            databaseManager.likeEstablishment(id);
            updateEstablishmentCounts(id, likeCountLabel, dislikeCountLabel);
            disableButtons(likeButton, dislikeButton);  // Disable buttons after action
        } catch (SQLException e) {
            handleDatabaseError(e, "Error liking establishment.");
        }
    }

    public void dislikeEstablishment(int id, JButton likeButton, JButton dislikeButton, JLabel likeCountLabel, JLabel dislikeCountLabel) {
        try {
            databaseManager.dislikeEstablishment(id);
            updateEstablishmentCounts(id, likeCountLabel, dislikeCountLabel);
            disableButtons(likeButton, dislikeButton);  // Disable buttons after action
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
    public void removeLike(int establishmentId, JButton likeButton, JButton dislikeButton, JLabel likeCountLabel, JLabel dislikeCountLabel) throws SQLException {
        // Decrease the like count in the database
        // Example:
        int updatedLikesCount = databaseManager.getUpdatedLikeCountFromDatabase(establishmentId);

        // Update the like button appearance
        likeButton.setIcon(new ImageIcon(new ImageIcon("src/main/resources/com/example/rating/likee")
                .getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH))); // Placeholder image or default icon

        // Update the UI elements
        likeCountLabel.setText("Likes: " + updatedLikesCount);

        // Optionally, disable the "like" button or change its state if needed
    }

    public void removeDislike(int establishmentId, JButton likeButton, JButton dislikeButton, JLabel likeCountLabel, JLabel dislikeCountLabel) throws SQLException {
        // Decrease the dislike count in the database
        int updatedDislikesCount = databaseManager.getUpdatedDislikeCountFromDatabase(establishmentId);

        // Update the dislike button appearance (for example, changing the icon to indicate it's not disliked anymore)
        dislikeButton.setIcon(new ImageIcon(new ImageIcon("src/main/resources/com/example/rating/dislikee")
                .getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH))); // Placeholder image or default icon

        // Update the UI elements
        dislikeCountLabel.setText("Dislikes: " + updatedDislikesCount); // Update the dislike count label

        // Optionally, disable the "dislike" button or change its state if needed (e.g., after removal)
        dislikeButton.setEnabled(true);  // Re-enable or reset button state as needed
    }
    // Method to retrieve filtered establishments based on search query and category
    public List<Establishment> getFilteredEstablishments(String searchQuery, String selectedCategory) throws SQLException {
        return databaseManager.getFilteredEstablishments(searchQuery, selectedCategory);
    }
    public List<Establishment> getAllEstablishments() throws SQLException {
     return databaseManager.getAllEstablishments();
    }
    public void addFavourite(Establishment establishment) {
        int currentUserId = 1;
        Favourite favourite = new Favourite(currentUserId, establishment.getId());
        if (databaseManager.addFavourite(favourite)) {
            System.out.println("Establishment added to favourites.");
        } else {
            System.out.println("Failed to add establishment to favourites.");
        }
    }

    public void removeFavourite(Establishment establishment) {
        // Logic for removing the favourite from the database
        // Implement similar to addFavourite if required
    }
}
