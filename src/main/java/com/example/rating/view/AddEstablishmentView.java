package com.example.rating.view;

import javax.swing.*;
import java.awt.*;

public class AddEstablishmentView {
    private JPanel panel;

    public AddEstablishmentView() {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(240, 240, 240));

        // Title Label
        JLabel titleLabel = new JLabel("Add New Establishment");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        titleLabel.setForeground(new Color(50, 50, 50));

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Form Fields
        addFormField(formPanel, gbc, "Name:", new JTextField());
        addFormField(formPanel, gbc, "Address:", new JTextField());
        addFormField(formPanel, gbc, "Description:", new JTextField());
        addFormField(formPanel, gbc, "Category:", new JTextField());

        // Submit Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        JButton submitButton = new JButton("Add Establishment");
        styleButton(submitButton);
        buttonPanel.add(submitButton);

        // Adding components to the main panel
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addFormField(JPanel formPanel, GridBagConstraints gbc, String labelText, JComponent field) {
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setForeground(new Color(50, 50, 50));
        formPanel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        if (field instanceof JTextField) {
            ((JTextField) field).setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        }
        formPanel.add(field, gbc);
        gbc.weightx = 0;
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(50, 100, 150)),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public JPanel getView() {
        return panel;
    }
}
