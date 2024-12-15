package com.example.rating.view;

import javax.swing.*;
import java.awt.*;

public class AddEstablishmentView {
    private JPanel panel;

    public AddEstablishmentView() {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel label = new JLabel("Add Establishment");
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        // Add form elements (example)
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 2, 10, 10));
        formPanel.add(new JLabel("Name:"));
        formPanel.add(new JTextField());
        formPanel.add(new JLabel("Address:"));
        formPanel.add(new JTextField());
        formPanel.add(new JLabel("Description:"));
        formPanel.add(new JTextField());
        formPanel.add(new JLabel("Category:"));
        formPanel.add(new JTextField());

        // Add submit button
        JButton submitButton = new JButton("Add");
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(label, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(submitButton, BorderLayout.SOUTH);
    }

    public JPanel getView() {
        return panel;
    }
}
