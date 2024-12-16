package com.example.rating.view;

import com.example.rating.model.Establishment;
import com.example.rating.controller.EstablishmentController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ViewEstablishmentsView {
    private JPanel panel;
    private JPanel listPanel;
    private EstablishmentController establishmentController;

    public ViewEstablishmentsView() {
        panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245)); // Light background color for the main panel

        // Title label with updated style
        JLabel label = createTitleLabel("Explore Establishments");

        // List panel to display establishments
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(Color.WHITE);
        listPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Padding for the panel

        panel.add(label, BorderLayout.NORTH);
        panel.add(new JScrollPane(listPanel), BorderLayout.CENTER);
    }

    private JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(new Color(50, 50, 150)); // Stylish color for the title
        label.setBorder(new EmptyBorder(20, 0, 20, 0)); // Spacing around the label
        return label;
    }

    public void updateList(List<Establishment> establishments) {
        listPanel.removeAll(); // Clear previous content

        // Add each establishment to the list with updated style
        for (Establishment establishment : establishments) {
            JPanel establishmentPanel = createEstablishmentPanel(establishment);
            listPanel.add(establishmentPanel);
            listPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Space between establishments
        }

        // Revalidate and repaint to reflect changes
        listPanel.revalidate();
        listPanel.repaint();
    }

    private JPanel createEstablishmentPanel(Establishment establishment) {
        JPanel establishmentPanel = new JPanel();
        establishmentPanel.setLayout(new BoxLayout(establishmentPanel, BoxLayout.Y_AXIS));
        establishmentPanel.setBackground(Color.WHITE);
        establishmentPanel.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 2));

        // Header section with establishment name and category
        JPanel headerPanel = createHeaderPanel(establishment);

        // Image placeholder
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon("path/to/placeholder-image.jpg")); // Placeholder image
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Description section with text area
        JTextArea descriptionArea = new JTextArea(establishment.getDescription());
        descriptionArea.setEditable(false);
        descriptionArea.setBackground(Color.WHITE);
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 12));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setPreferredSize(new Dimension(300, 60)); // Limited size for the description

        // Footer with Like/Dislike icons
        JPanel footerPanel = createFooterPanel(establishment);

        // Add components to the establishment panel
        establishmentPanel.add(headerPanel);
        establishmentPanel.add(imageLabel);
        establishmentPanel.add(descriptionArea);
        establishmentPanel.add(footerPanel);

        return establishmentPanel;
    }

    private JPanel createHeaderPanel(Establishment establishment) {
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel(establishment.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setForeground(new Color(50, 50, 150));

        JLabel categoryLabel = new JLabel("• " + establishment.getCategory());
        categoryLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        categoryLabel.setForeground(new Color(100, 100, 100));

        headerPanel.add(nameLabel);
        headerPanel.add(categoryLabel);

        return headerPanel;
    }

    private JPanel createFooterPanel(Establishment establishment) {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        footerPanel.setBackground(Color.WHITE);

        // Like button
        JButton likeButton = new JButton(new ImageIcon(new ImageIcon("src/main/resources/com/example/rating/likee")
                .getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH))); // Resize the image to fit the button
        likeButton.setPreferredSize(new Dimension(40, 40)); // Set a fixed size for the button
        likeButton.setBackground(Color.WHITE);
        likeButton.setFocusPainted(false);
        likeButton.setBorder(BorderFactory.createEmptyBorder());
        likeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Dislike button
        JButton dislikeButton = new JButton(new ImageIcon(new ImageIcon("src/main/resources/com/example/rating/dislikee")
                .getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH))); // Resize the image to fit the button
        dislikeButton.setPreferredSize(new Dimension(40, 40)); // Set a fixed size for the button
        dislikeButton.setBackground(Color.WHITE);
        dislikeButton.setFocusPainted(false);
        dislikeButton.setBorder(BorderFactory.createEmptyBorder());
        dislikeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Labels to show the counts of likes and dislikes
        JLabel likeCountLabel = new JLabel("Likes: " + establishment.getLikesCount());
        likeCountLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        likeCountLabel.setForeground(Color.GRAY);
        JLabel dislikeCountLabel = new JLabel("Dislikes: " + establishment.getDislikesCount());
        dislikeCountLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        dislikeCountLabel.setForeground(Color.GRAY);

        // Add mouse listeners for the buttons
        likeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                establishmentController.likeEstablishment(establishment.getId(), likeButton, dislikeButton, likeCountLabel, dislikeCountLabel);
            }
        });

        dislikeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                establishmentController.dislikeEstablishment(establishment.getId(), likeButton, dislikeButton, likeCountLabel, dislikeCountLabel);
            }
        });

        // Add components to the footer panel
        footerPanel.add(likeButton);
        footerPanel.add(dislikeButton);
        footerPanel.add(likeCountLabel);
        footerPanel.add(dislikeCountLabel);

        return footerPanel;
    }


    public JPanel getView() {
        return panel;
    }
}
