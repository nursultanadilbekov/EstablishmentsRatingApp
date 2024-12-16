package com.example.rating.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import com.example.rating.controller.EstablishmentController;
import com.example.rating.model.Establishment;

public class AddEstablishmentView {
    private JPanel panel;
    private JTextField nameField;
    private JTextField addressField;
    private JTextField descriptionField;
    private JButton submitButton;
    private JComboBox<String> categoryField;
    private EstablishmentController establishmentController;

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
        nameField = new JTextField();
        addressField = new JTextField();
        descriptionField = new JTextField();
        categoryField = new JComboBox<>(new String[]{"Restaurant", "Cafe", "Bar", "Hotel"});

        addFormField(formPanel, gbc, "Name:", nameField);
        addFormField(formPanel, gbc, "Address:", addressField);
        addFormField(formPanel, gbc, "Description:", descriptionField);
        addFormField(formPanel, gbc, "Category:", categoryField);

        // Submit Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        submitButton = new JButton("Add Establishment");
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

        // Apply styling for category field (JComboBox) to look like a button
        if (field instanceof JComboBox) {
            JComboBox<?> comboBox = (JComboBox<?>) field;
            comboBox.setBackground(new Color(70, 130, 180)); // Background color same as button
            comboBox.setForeground(Color.WHITE); // Text color white
            comboBox.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(50, 100, 150)),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5))); // Border similar to button

            // Style for the drop-down arrow and selected item
            comboBox.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    label.setFont(new Font("Arial", Font.PLAIN, 14));
                    if (isSelected) {
                        label.setBackground(new Color(70, 130, 180));  // Same background color when selected
                        label.setForeground(Color.WHITE);  // White text when selected
                    } else {
                        label.setBackground(Color.WHITE);
                        label.setForeground(new Color(50, 50, 50));  // Default text color
                    }
                    return label;
                }
            });
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

    // Get form values
    public String getName() {
        return nameField.getText();
    }

    public String getAddress() {
        return addressField.getText();
    }

    public String getDescription() {
        return descriptionField.getText();
    }

    public String getCategory() {
        return Objects.requireNonNull(categoryField.getSelectedItem()).toString();
    }
    public int getCategoryId() {
        int prefix;
        String category = getCategory();

        // Determine the prefix based on the category name
        switch (category.toLowerCase()) {
            case "restaurant":
                prefix = 1;
                break;
            case "bar":
                prefix = 2;
                break;
            case "cafe":
                prefix = 3;
                break;
            case "hotel":
                prefix = 4;
                break;
            default:
                throw new IllegalArgumentException("Unsupported category: " + category);
        }

        // Combine prefix and next number to form the ID
        return prefix;
    }

    // Method to add an ActionListener to the submit button
    public void addSubmitButtonListener(ActionListener listener) {
        submitButton.addActionListener(listener);
    }
    // Clear all input fields and reset to initial state
    public void clearFormFields() {
        nameField.setText(""); // Clear the name field
        addressField.setText(""); // Clear the address field
        descriptionField.setText(""); // Clear the description field
        categoryField.setSelectedIndex(0); // Reset the combo box to the first item
    }

}
