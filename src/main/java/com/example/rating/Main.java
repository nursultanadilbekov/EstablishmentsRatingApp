package com.example.rating;

import com.example.rating.controller.EstablishmentController;
import com.example.rating.view.DialogUtil;
import com.example.rating.view.WelcomeView;

import javax.swing.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Initialize the controller
                EstablishmentController controller = new EstablishmentController();

                // Launch WelcomeView
                new WelcomeView(controller);
            } catch (SQLException e) {
                e.printStackTrace();

                // Show error message using DialogUtil
                DialogUtil.showMessage(
                        "An error occurred while connecting to the database. Please try again later.",
                        "Database Connection Error",
                        true
                );
            }
        });
    }
}
