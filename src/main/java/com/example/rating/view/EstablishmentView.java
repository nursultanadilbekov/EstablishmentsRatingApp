package com.example.rating.view;

import com.example.rating.model.Establishment;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EstablishmentView {
    private JPanel cardPanel;  // This panel will hold different views
    private CardLayout cardLayout;

    public EstablishmentView() {
        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        // Create views for each functionality
        JPanel addEstablishmentPanel = createAddEstablishmentPanel();
        JPanel topEstablishmentsPanel = createEstablishmentsPanel("Top Establishments");
        JPanel viewEstablishmentsPanel = createEstablishmentsPanel("View Establishments");

        // Add views to the CardLayout
        cardPanel.add(addEstablishmentPanel, "AddEstablishment");
        cardPanel.add(topEstablishmentsPanel, "TopEstablishments");
        cardPanel.add(viewEstablishmentsPanel, "ViewEstablishments");
    }

    // Create panel for adding a new establishment
    private JPanel createAddEstablishmentPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel label = new JLabel("Add Establishment");
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label, BorderLayout.CENTER);

        return panel;
    }

    // Helper method to create panels for listing establishments
    private JPanel createEstablishmentsPanel(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel label = new JLabel(title);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label, BorderLayout.NORTH);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BorderLayout());
        panel.add(listPanel, BorderLayout.CENTER);

        return panel;
    }

    public JPanel getView() {
        return cardPanel;
    }

    // Show the selected view based on its name
    public void showView(String viewName) {
        cardLayout.show(cardPanel, viewName);
    }

    public void showTopEstablishments(List<Establishment> establishments) {
        updateEstablishmentList(1, establishments);
    }

    public void showEstablishments(List<Establishment> establishments) {
        updateEstablishmentList(2, establishments);
    }

    private void updateEstablishmentList(int panelIndex, List<Establishment> establishments) {
        JPanel establishmentPanel = (JPanel) cardPanel.getComponent(panelIndex);
        JPanel listPanel = (JPanel) establishmentPanel.getComponent(1); // Get the panel where the list is shown
        listPanel.removeAll();

        DefaultListModel<String> model = new DefaultListModel<>();
        for (Establishment establishment : establishments) {
            model.addElement(establishment.getName() + " - " + establishment.getCategory());
        }

        JList<String> list = new JList<>(model);
        JScrollPane scrollPane = new JScrollPane(list);
        listPanel.add(scrollPane, BorderLayout.CENTER);

        listPanel.revalidate();
        listPanel.repaint();
    }
}
