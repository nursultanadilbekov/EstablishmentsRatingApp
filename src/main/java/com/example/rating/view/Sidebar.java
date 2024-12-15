package com.example.rating.view;

import com.example.rating.controller.EstablishmentController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.function.Consumer;

public class Sidebar {
    private final JPanel sidebarPanel;

    public Sidebar(EstablishmentController controller, Consumer<JPanel> setViewCallback) {
        sidebarPanel = createSidebar(controller, setViewCallback);
    }

    private JPanel createSidebar(EstablishmentController controller, Consumer<JPanel> setViewCallback) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(50, 50, 50));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Header label
        JLabel title = createTitleLabel("Menu");
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Buttons with actions to update the main view
        panel.add(createButton("Add Establishment",
                () -> setViewCallback.accept(controller.getAddEstablishmentView())));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(createButton("Top Establishments",
                () -> setViewCallback.accept(controller.getTopEstablishmentsView())));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(createButton("View Establishments",
                () -> setViewCallback.accept(controller.getViewEstablishmentsView())));
        panel.add(Box.createVerticalGlue());

        return panel;
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

    public JPanel getView() {
        return sidebarPanel;
    }
}
