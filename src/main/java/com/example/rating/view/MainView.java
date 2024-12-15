package com.example.rating.view;

import com.example.rating.controller.EstablishmentController;

import javax.swing.*;
import java.awt.*;

public class MainView {
    private final JFrame frame;
    private final JPanel mainPanel;
    private final Sidebar sidebar;

    public MainView(EstablishmentController controller) {
        frame = createFrame();
        sidebar = new Sidebar(controller, this::setView); // Pass a callback to update the view
        mainPanel = new JPanel(new BorderLayout());

        // Initial layout: Add the sidebar and default view
        mainPanel.add(sidebar.getView(), BorderLayout.WEST);
        mainPanel.add(controller.getViewEstablishmentsView(), BorderLayout.CENTER);

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

    /**
     * Updates the main content area with a new view.
     *
     * @param newView the new JPanel to display in the main content area
     */
    private void setView(JPanel newView) {
        // Remove the current CENTER component and replace it with the new view
        mainPanel.remove(1); // Remove the CENTER component (index 1 because WEST is index 0)
        mainPanel.add(newView, BorderLayout.CENTER);

        // Revalidate and repaint to apply the changes
        mainPanel.revalidate();
        mainPanel.repaint();
    }
}
