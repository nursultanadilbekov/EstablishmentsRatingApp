package com.example.rating.view;

import com.example.rating.controller.EstablishmentController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton registerButton;
    private EstablishmentController controller;

    public RegistrationView(EstablishmentController controller, WelcomeView welcomeView) {
        this.controller = controller;

        setTitle("Register");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);  // Center the window
        setLayout(new BorderLayout());

        // Set background color and padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(50, 50, 50));  // Dark background
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        // Create UI components with styles
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);
        registerButton = new JButton("Register");

        // Set text field and button styles
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setForeground(Color.WHITE);
        registerButton.setBackground(new Color(70, 130, 180));  // Blue background
        registerButton.setFocusPainted(false);  // Remove focus border
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));  // Hand cursor on hover

        // Create labels with text color and alignment
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        usernameLabel.setForeground(Color.WHITE);
        passwordLabel.setForeground(Color.WHITE);
        confirmPasswordLabel.setForeground(Color.WHITE);

        // Add components to the main panel
        mainPanel.add(usernameLabel);
        mainPanel.add(usernameField);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);
        mainPanel.add(confirmPasswordLabel);
        mainPanel.add(confirmPasswordField);

        // Create a separate panel for the register button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(50, 50, 50));  // Same dark background
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));  // Add some space above the button
        buttonPanel.add(registerButton);

        // Add action listener for the register button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                // Validate input fields
                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill all fields.");
                } else if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(null, "Passwords do not match.");
                } else {
                    // Call the controller to register the user
                    boolean success = controller.registerUser(username, password);
                    if (success) {
                        DialogUtil.showMessage("Registration successful!", "Success", false);
                        setVisible(false);  // Close registration screen
                        new LoginView(controller, welcomeView); // Show login screen
                    } else {
                        DialogUtil.showMessage("Username is already taken.", "Error", true);
                    }
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

        // Display the registration window
        setVisible(true);
    }
}
