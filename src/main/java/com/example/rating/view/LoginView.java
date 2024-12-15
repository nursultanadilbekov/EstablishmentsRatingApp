package com.example.rating.view;

import com.example.rating.controller.EstablishmentController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private EstablishmentController controller;

    public LoginView(EstablishmentController controller) {
        this.controller = controller;

        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        mainPanel.add(loginButton);

        // Add action listener for the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Call the controller method to validate the user login
                if (controller.validateLogin(username, password)) {
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    dispose();  // Close login screen
                    new MainView(controller);  // Open main view (adjust this to your needs)
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password.");
                }
            }
        });

        // Add the main panel to the frame
        add(mainPanel, BorderLayout.CENTER);

        // Display the login window
        setVisible(true);
    }
}
