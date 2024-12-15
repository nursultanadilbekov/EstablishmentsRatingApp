package com.example.rating.view;

import javax.swing.*;
import java.awt.*;

public class DialogUtil {

    // Initialize the UIManager for consistent dialog styling
    static {
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 14));
        UIManager.put("OptionPane.messageForeground", Color.WHITE);  // White text for consistency
        UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.BOLD, 14));
        UIManager.put("OptionPane.buttonForeground", Color.WHITE);
        UIManager.put("Button.background", new Color(70, 130, 180));  // Blue button background
        UIManager.put("OptionPane.background", new Color(50, 50, 50));  // Dark background for dialogs
        UIManager.put("Panel.background", new Color(50, 50, 50));  // Dark background for panel
    }

    // Method to display a dialog message
    public static void showMessage(String message, String title, boolean isError) {
        // Create the JOptionPane with the appropriate message type
        JOptionPane optionPane = new JOptionPane(
                message,
                isError ? JOptionPane.ERROR_MESSAGE : JOptionPane.INFORMATION_MESSAGE
        );

        // Create a dialog using the JOptionPane
        JDialog dialog = optionPane.createDialog(title);

        // Additional customization of the dialog (if needed)
        dialog.getContentPane().setBackground(new Color(50, 50, 50));  // Dark background
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }
}
