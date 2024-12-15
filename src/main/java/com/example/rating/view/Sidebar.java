package com.example.rating.view;

import com.example.rating.controller.EstablishmentController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Sidebar {
    private final JPanel sidebar;
    private final JPanel mainPanel;

    public Sidebar(EstablishmentController establishmentController) {
        sidebar = createSidebar(establishmentController);
        mainPanel = createMainPanel(establishmentController);
    }

    private JPanel createSidebar(EstablishmentController establishmentController) {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(50, 50, 50));
        sidebar.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Header label
        JLabel title = createTitleLabel("Menu");
        sidebar.add(title);
        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));

        // Buttons
        sidebar.add(createButton("Add Establishment", () -> establishmentController.handleButtonClick("AddEstablishment")));
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(createButton("Top Establishments", () -> establishmentController.handleButtonClick("TopEstablishments")));
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(createButton("View Establishments", () -> establishmentController.handleButtonClick("ViewEstablishments")));
        sidebar.add(Box.createVerticalGlue());

        return sidebar;
    }

    private JPanel createMainPanel(EstablishmentController establishmentController) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(establishmentController.getViewEstablishmentsView(), BorderLayout.CENTER);
        return mainPanel;
    }

    private JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JButton createButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(70, 130, 180));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(e -> action.run());
        return button;
    }

    public void setPreferredSize(Dimension dimension) {
        sidebar.setPreferredSize(dimension);
    }

    public JPanel getView() {
        return mainPanel;
    }
}
