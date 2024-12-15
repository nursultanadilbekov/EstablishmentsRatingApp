package com.example.rating.view;

import com.example.rating.controller.EstablishmentController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Sidebar {
    private JPanel sidebar;
    private JPanel mainPanel;

    public Sidebar(EstablishmentController establishmentController) {
        sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(50, 50, 50)); // Dark background
        sidebar.setBorder(new EmptyBorder(20, 20, 20, 20)); // Padding around the sidebar

        // Menu header
        JLabel title = new JLabel("Menu");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Buttons with listeners for different views
        JButton addEstablishmentButton = createStyledButton("Add Establishment");
        addEstablishmentButton.addActionListener(e -> establishmentController.handleButtonClick("AddEstablishment"));

        JButton topEstablishmentsButton = createStyledButton("Top Establishments");
        topEstablishmentsButton.addActionListener(e -> establishmentController.handleButtonClick("TopEstablishments"));

        JButton viewEstablishmentsButton = createStyledButton("View Establishments");
        viewEstablishmentsButton.addActionListener(e -> establishmentController.handleButtonClick("ViewEstablishments"));

        // Adding buttons to the sidebar
        sidebar.add(title);
        sidebar.add(Box.createRigidArea(new Dimension(0, 20))); // Divider
        sidebar.add(addEstablishmentButton);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(topEstablishmentsButton);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(viewEstablishmentsButton);
        sidebar.add(Box.createVerticalGlue()); // Flexible divider

        // Combine sidebar and main content panel
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(establishmentController.getView(), BorderLayout.CENTER); // Adding the view from the controller
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setForeground(Color.WHITE); // White text
        button.setBackground(new Color(70, 130, 180)); // Blue background
        button.setFocusPainted(false); // Remove focus border
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15)); // Padding
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Hand cursor on hover
        return button;
    }

    // Method to set preferred size for the sidebar
    public void setPreferredSize(Dimension dimension) {
        sidebar.setPreferredSize(dimension);
    }

    public JPanel getView() {
        return mainPanel;
    }
}
