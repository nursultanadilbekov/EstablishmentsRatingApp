package com.example.rating.view;

import com.example.rating.controller.EstablishmentController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    public JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private EstablishmentController controller;

    public LoginView(EstablishmentController controller, WelcomeView welcomeView) {
        this.controller = controller;

        setTitle("Login");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);  // Center the window
        setLayout(new BorderLayout());

        // Set background color and padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(50, 50, 50));  // Dark background
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create UI components with styles
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");

        // Set text field and button styles
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(new Color(70, 130, 180));  // Blue background
        loginButton.setFocusPainted(false);  // Remove focus border
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));  // Hand cursor on hover

        // Create labels with text color and alignment
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameLabel.setForeground(Color.WHITE);
        passwordLabel.setForeground(Color.WHITE);

        // Add components to the main panel
        mainPanel.add(usernameLabel);
        mainPanel.add(usernameField);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);

        // Create a separate panel for the login button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(50, 50, 50));  // Same dark background
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));  // Add some space above the button
        buttonPanel.add(loginButton);

        // Add action listener for the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (controller.validateLogin(username, password)) {
                    DialogUtil.showMessage("Login successful!", "Success", false);
                    setVisible(false); // Close login screen
                    new MainView(controller);  // Open main view
                } else {
                    DialogUtil.showMessage("Invalid username or password.", "Error", true);
                }
            }
        });

        // Add the main panel to the frame
        add(mainPanel, BorderLayout.CENTER);

        // Add the button panel to the bottom of the frame
        add(buttonPanel, BorderLayout.SOUTH);

        // Add a window listener to show the WelcomeView when this window is closed
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                welcomeView.setVisible(true);
            }
        });

        // Display the login window
        setVisible(true);
    }
}
