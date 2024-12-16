package com.example.rating.view;

import com.example.rating.model.Establishment;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TopEstablishmentsView {
    private JPanel panel;
    private JPanel postPanel;

    public TopEstablishmentsView() {
        panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel label = new JLabel("Top Establishments", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(new Color(50, 50, 150)); // Stylish color for the title

        postPanel = new JPanel();
        postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));
        postPanel.setBackground(Color.WHITE);
        postPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add padding to the panel

        panel.add(label, BorderLayout.NORTH);
        panel.add(new JScrollPane(postPanel), BorderLayout.CENTER);
    }

    public JPanel getView() {
        return panel;
    }

    public void updateList(List<Establishment> establishments) {
        postPanel.removeAll();

        for (Establishment establishment : establishments) {
            JPanel post = createPostPanel(establishment);
            postPanel.add(post);
            postPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Space between posts
        }

        postPanel.revalidate();
        postPanel.repaint();
    }

    private JPanel createPostPanel(Establishment establishment) {
        JPanel postPanel = new JPanel();
        postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));
        postPanel.setBackground(Color.WHITE);
        postPanel.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

        // Post Header: Establishment Name and Category
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(Color.WHITE);
        JLabel nameLabel = new JLabel(establishment.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel categoryLabel = new JLabel("• " + establishment.getCategory());
        categoryLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        categoryLabel.setForeground(new Color(100, 100, 100));

        headerPanel.add(nameLabel);
        headerPanel.add(categoryLabel);

        // Post Image (if any) or placeholder
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon("path/to/placeholder-image.jpg")); // Placeholder image
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Post Content: Description and Stats
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);

        JTextArea descriptionArea = new JTextArea(establishment.getDescription());
        descriptionArea.setEditable(false);
        descriptionArea.setBackground(Color.WHITE);
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 12));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setPreferredSize(new Dimension(300, 60)); // Limit the description size

        contentPanel.add(descriptionArea);

        // Post Footer: Likes, Dislikes, and Follow Button
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        footerPanel.setBackground(Color.WHITE);

        // Thumbs Up/Down for likes/dislikes
        JLabel likeIcon = new JLabel(establishment.getLikes() > 0 ? new ImageIcon("path/to/thumb-up-icon.png") : null);
        JLabel dislikeIcon = new JLabel(establishment.getDislikes() > 0 ? new ImageIcon("path/to/thumb-down-icon.png") : null);

        // Follow button
        JButton followButton = new JButton("Follow");
        followButton.setBackground(new Color(0, 148, 255));
        followButton.setForeground(Color.WHITE);
        followButton.setFont(new Font("Arial", Font.PLAIN, 12));
        followButton.setFocusPainted(false);
        followButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(panel, "You followed " + establishment.getName() + "!");
            }
        });

        footerPanel.add(likeIcon);
        footerPanel.add(dislikeIcon);
        footerPanel.add(followButton);

        // Add components to the post panel
        postPanel.add(headerPanel);
        postPanel.add(imageLabel);
        postPanel.add(contentPanel);
        postPanel.add(footerPanel);

        return postPanel;
    }
}
