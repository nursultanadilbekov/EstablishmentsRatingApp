package com.example.rating.view;

import com.example.rating.controller.EstablishmentController;

import javax.swing.*;
import java.awt.*;

public class MainView {
    private JFrame frame;
    private JPanel mainPanel;
    private Sidebar sidebar;
    private boolean isSidebarVisible = true; // Track sidebar visibility state

    public MainView(EstablishmentController controller) {
        frame = new JFrame("Establishment Rater");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Create Sidebar and Content Panel
        sidebar = new Sidebar(controller);
        mainPanel = new JPanel(new BorderLayout());

        // Add Sidebar to the main panel initially
        mainPanel.add(sidebar.getView(), BorderLayout.WEST);

        // Add the toggle button for the sidebar
        JButton toggleSidebarButton = createStyledButton("-");
        toggleSidebarButton.addActionListener(e -> toggleSidebarVisibility());

        // Adding the button to the top panel (you can adjust the position/style here)
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Left align the button
        topPanel.setBackground(new Color(50, 50, 50)); // Same background as sidebar
        topPanel.add(toggleSidebarButton);
        mainPanel.add(topPanel, BorderLayout.NORTH); // Add the top panel to the main layout

        // Adding the content panel (EstablishmentView) to the center
        mainPanel.add(controller.getView(), BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    private void toggleSidebarVisibility() {
        // Toggle the visibility state of the sidebar
        isSidebarVisible = !isSidebarVisible;

        // Adjust the layout based on the visibility state of the sidebar
        if (isSidebarVisible) {
            mainPanel.add(sidebar.getView(), BorderLayout.WEST);  // Show the sidebar
        } else {
            mainPanel.remove(sidebar.getView()); // Hide the sidebar
        }

        // Revalidate and repaint the main panel to apply changes
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setForeground(Color.WHITE); // White text
        button.setBackground(new Color(70, 130, 180)); // Blue background (same as sidebar buttons)
        button.setFocusPainted(false); // Remove focus border
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15)); // Padding
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Hand cursor
        return button;
    }
}
