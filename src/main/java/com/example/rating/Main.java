package com.example.rating;

import com.example.rating.controller.EstablishmentController;
import com.example.rating.model.DatabaseManager;
import com.example.rating.view.LoginView;
import com.example.rating.view.RegistrationView;

import javax.swing.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                EstablishmentController controller = new EstablishmentController();

                // Prompt user to either login or register
                int choice = JOptionPane.showOptionDialog(null, "Choose an option", "Welcome",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                        null, new Object[]{"Login", "Register"}, "Login");

                if (choice == 0) {
                    new LoginView(controller);  // Open login screen
                } else if (choice == 1) {
                    new RegistrationView(controller);  // Open registration screen
                }

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error connecting to database");
            }
        });
    }
}
