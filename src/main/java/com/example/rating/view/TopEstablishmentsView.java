package com.example.rating.view;

import com.example.rating.model.Establishment;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TopEstablishmentsView {
    private JPanel panel;
    private JPanel postPanel;
    private List<Establishment> allEstablishments;
    private JComboBox<String> categoryFilter;
    private JTextField searchField;

    public TopEstablishmentsView() {
        panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // Title Label
        JLabel label = new JLabel("Top Establishments", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(new Color(50, 50, 150));

        // Filter Panel (Category, Search by Name)
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBackground(Color.WHITE);

        // Category Filter
        categoryFilter = new JComboBox<>();
        categoryFilter.addItem("All Categories");
        categoryFilter.addItem("Restaurant");
        categoryFilter.addItem("Cafe");
        categoryFilter.addItem("Hotel");
        // Add more categories as needed
        categoryFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateList();
            }
        });

        // Search by Name
        searchField = new JTextField(15);
        searchField.setFont(new Font("Arial", Font.PLAIN, 12));
        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateList();
            }
        });

        filterPanel.add(new JLabel("Filter by Category:"));
        filterPanel.add(categoryFilter);
        filterPanel.add(new JLabel("Search by Name:"));
        filterPanel.add(searchField);

        // Establishments Panel
        postPanel = new JPanel();
        postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));
        postPanel.setBackground(Color.WHITE);
        postPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        panel.add(label, BorderLayout.NORTH);
        panel.add(filterPanel, BorderLayout.SOUTH);
        panel.add(new JScrollPane(postPanel), BorderLayout.CENTER);
    }

    public JPanel getView() {
        return panel;
    }

    public void setEstablishments(List<Establishment> establishments) {
        if (establishments != null) {
            this.allEstablishments = establishments;
            updateList();
        }
    }
    public void updateList() {
        postPanel.removeAll();

        // Filter by Category
        String selectedCategory = (String) categoryFilter.getSelectedItem();
        String searchText = searchField.getText().toLowerCase();

        List<Establishment> filteredEstablishments = allEstablishments.stream()
                .filter(establishment -> (selectedCategory.equals("All Categories") || establishment.getCategory().equalsIgnoreCase(selectedCategory)))
                .filter(establishment -> establishment.getName().toLowerCase().contains(searchText))
                .collect(Collectors.toList());

        // Sort by Like Percentage
        filteredEstablishments.sort(new Comparator<Establishment>() {
            @Override
            public int compare(Establishment e1, Establishment e2) {
                double likePercentage1 = (double) e1.getLikes() / (e1.getLikes() + e1.getDislikes()) * 100;
                double likePercentage2 = (double) e2.getLikes() / (e2.getLikes() + e2.getDislikes()) * 100;
                return Double.compare(likePercentage2, likePercentage1); // Descending order
            }
        });

        // Create and add post panels
        for (Establishment establishment : filteredEstablishments) {
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
        imageLabel.setIcon(new ImageIcon("path/to/placeholder-image.jpg"));
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
        descriptionArea.setPreferredSize(new Dimension(300, 60));

        contentPanel.add(descriptionArea);

        // Post Footer: Likes, Dislikes, and Follow Button
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Thumbs Up/Down for likes/dislikes
        JPanel likesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        likesPanel.setBackground(Color.WHITE);

        JLabel likeIcon = new JLabel(establishment.getLikes() > 0 ? new ImageIcon("path/to/thumb-up-icon.png") : null);
        JLabel dislikeIcon = new JLabel(establishment.getDislikes() > 0 ? new ImageIcon("path/to/thumb-down-icon.png") : null);

        likesPanel.add(likeIcon);
        likesPanel.add(dislikeIcon);

        // Follow button
        JButton followButton = new JButton("Follow");
        followButton.setBackground(new Color(0, 148, 255));
        followButton.setForeground(Color.WHITE);
        followButton.setFont(new Font("Arial", Font.PLAIN, 12));
        followButton.setFocusPainted(false);
        followButton.setPreferredSize(new Dimension(100, 30));
        followButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(panel, "You followed " + establishment.getName() + "!");
            }
        });

        footerPanel.add(likesPanel);
        footerPanel.add(followButton);

        // Add components to the post panel
        postPanel.add(headerPanel);
        postPanel.add(imageLabel);
        postPanel.add(contentPanel);
        postPanel.add(footerPanel);

        return postPanel;
    }
}
