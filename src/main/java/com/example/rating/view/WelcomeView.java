package com.example.rating.view;

import com.example.rating.controller.EstablishmentController;

import javax.swing.*;
import java.awt.*;

public class WelcomeView extends JFrame {
    private JButton loginButton;
    private JButton registerButton;
    private EstablishmentController establishmentController;

    public WelcomeView(EstablishmentController controller) {
        this.establishmentController = controller;
        setTitle("Welcome");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Center the window

        // Create a main panel with a GridBagLayout to center the components
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(50, 50, 50));  // Dark background

        // Create UI components
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        // Set button styles
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setForeground(Color.WHITE);
        registerButton.setForeground(Color.WHITE);
        loginButton.setBackground(new Color(70, 130, 180));  // Blue background
        registerButton.setBackground(new Color(100, 149, 237));  // Light blue background
        loginButton.setFocusPainted(false);
        registerButton.setFocusPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add action listeners
        loginButton.addActionListener(e -> {
            setVisible(false);  // Hide Welcome screen
            new LoginView(controller, this);  // Pass the WelcomeView instance to LoginView
        });

        registerButton.addActionListener(e -> {
            setVisible(false);  // Hide Welcome screen
            new RegistrationView(controller, this);  // Pass the WelcomeView instance to RegistrationView
        });

        // Add buttons to the main panel using GridBagConstraints for centering
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0);  // Add vertical spacing between buttons
        gbc.fill = GridBagConstraints.HORIZONTAL;

        mainPanel.add(loginButton, gbc);

        gbc.gridy++;
        mainPanel.add(registerButton, gbc);

        // Add the main panel to the frame
        add(mainPanel, BorderLayout.CENTER);

        // Display the window
        setVisible(true);
    }
}