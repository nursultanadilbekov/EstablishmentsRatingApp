package com.example.rating.view;

import com.example.rating.controller.EstablishmentController;

import javax.swing.*;
import java.awt.*;

public class MainView {
    private final JFrame frame;
    private final JPanel mainPanel;
    private final Sidebar sidebar;
    private boolean isSidebarVisible = true;

    public MainView(EstablishmentController controller) {
        frame = createFrame();
        sidebar = new Sidebar(controller);
        mainPanel = createMainPanel(controller);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private JFrame createFrame() {
        JFrame frame = new JFrame("Establishment Rater");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());
        return frame;
    }

    private JPanel createMainPanel(EstablishmentController controller) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(sidebar.getView(), BorderLayout.WEST);
        mainPanel.add(createTopPanel(), BorderLayout.NORTH);
        mainPanel.add(controller.getViewEstablishmentsView(), BorderLayout.CENTER);
        return mainPanel;
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(50, 50, 50));
        JButton toggleSidebarButton = createButton("-", this::toggleSidebarVisibility);
        topPanel.add(toggleSidebarButton);
        return topPanel;
    }

    private void toggleSidebarVisibility() {
        isSidebarVisible = !isSidebarVisible;
        if (isSidebarVisible) {
            mainPanel.add(sidebar.getView(), BorderLayout.WEST);
        } else {
            mainPanel.remove(sidebar.getView());
        }
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private JButton createButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(70, 130, 180));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(e -> action.run());
        return button;
    }
}
