package com.example.rating.view;

import com.example.rating.model.Establishment;
import com.example.rating.controller.EstablishmentController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ViewEstablishmentsView {
    private EstablishmentController establishmentController;
    private JPanel panel;
    private JPanel listPanel;

    public ViewEstablishmentsView() {
        // Initialize the main panel with BorderLayout
        panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // Add the title label
        JLabel label = new JLabel("Explore Establishments", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setForeground(new Color(50, 50, 150));

        // Initialize the listPanel to display establishments
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS)); // Stack items vertically

        // Add components to the main panel
        panel.add(label, BorderLayout.NORTH);
        panel.add(new JScrollPane(listPanel), BorderLayout.CENTER);
    }

    public void updateList(List<Establishment> establishments) {
        listPanel.removeAll(); // Clear the previous content

        // Iterate through each establishment and create a panel for each
        for (Establishment establishment : establishments) {
            JPanel establishmentPanel = createEstablishmentPanel(establishment);
            listPanel.add(establishmentPanel); // Add the panel for the current establishment
        }

        // Revalidate and repaint the panel to reflect changes
        listPanel.revalidate();
        listPanel.repaint();
    }

    private JPanel createEstablishmentPanel(Establishment establishment) {
        // Create panel to display establishment's details and buttons
        JPanel establishmentPanel = new JPanel();
        establishmentPanel.setLayout(new BoxLayout(establishmentPanel, BoxLayout.Y_AXIS));
        establishmentPanel.setBackground(Color.WHITE);
        establishmentPanel.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 2));
        establishmentPanel.setPreferredSize(new Dimension(500, 200));

        // Create panel for establishment's name and category
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel(establishment.getName() + " - " + establishment.getCategory());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setForeground(new Color(50, 50, 150));

        // Add profile-like image and name
        ImageIcon profileIcon = new ImageIcon("path/to/establishment-image.jpg"); // Placeholder image path
        JLabel profileImageLabel = new JLabel(profileIcon);
        profileImageLabel.setPreferredSize(new Dimension(80, 80));
        profileImageLabel.setIcon(new ImageIcon(profileIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH)));

        // Add name label
        infoPanel.add(profileImageLabel);
        infoPanel.add(nameLabel);

        // Add description below name and image
        JLabel descriptionLabel = new JLabel("<html>" + establishment.getDescription() + "</html>");
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionLabel.setForeground(new Color(100, 100, 100));
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the description

        // Panel for like/dislike image buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3)); // Like, Dislike, and Follow buttons
        buttonPanel.setBackground(Color.WHITE);

        // Like button (replace with your image path)
        ImageIcon likeIcon = new ImageIcon("src/main/resources/com/example/rating/like.png");
        Image likeImage = likeIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH); // Scale the image to 30x30
        likeIcon = new ImageIcon(likeImage);
        JLabel likeImageLabel = new JLabel(likeIcon);

        // Dislike button (replace with your image path)
        ImageIcon dislikeIcon = new ImageIcon("src/main/resources/com/example/rating/dislike.png");
        Image dislikeImage = dislikeIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH); // Scale the image to 30x30
        dislikeIcon = new ImageIcon(dislikeImage);
        JLabel dislikeImageLabel = new JLabel(dislikeIcon);

        // Like button listener
        likeImageLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                handleLikeButtonClick(establishment.getId(), likeImageLabel, dislikeImageLabel);
            }
        });

        // Dislike button listener
        dislikeImageLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                handleDislikeButtonClick(establishment.getId(), likeImageLabel, dislikeImageLabel);
            }
        });

        // Add the like and dislike buttons to the panel
        buttonPanel.add(likeImageLabel);
        buttonPanel.add(dislikeImageLabel);

        // Add the infoPanel and buttonPanel to the establishmentPanel
        establishmentPanel.add(infoPanel);
        establishmentPanel.add(descriptionLabel); // Add description label
        establishmentPanel.add(buttonPanel);

        return establishmentPanel;
    }

    private void handleLikeButtonClick(int establishmentId, JLabel likeButton, JLabel dislikeButton) {
        // Perform like action
        establishmentController.likeEstablishment(establishmentId);
        likeButton.setEnabled(false);  // Disable like button
        dislikeButton.setEnabled(false);  // Disable dislike button
    }

    private void handleDislikeButtonClick(int establishmentId, JLabel likeButton, JLabel dislikeButton) {
        // Perform dislike action
        establishmentController.dislikeEstablishment(establishmentId);
        likeButton.setEnabled(false);  // Disable like button
        dislikeButton.setEnabled(false);  // Disable dislike button
    }

    public JPanel getView() {
        return panel;
    }
}
