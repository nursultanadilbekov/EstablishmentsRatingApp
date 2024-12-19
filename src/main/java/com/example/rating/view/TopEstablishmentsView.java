package com.example.rating.view;

import com.example.rating.controller.EstablishmentController;
import com.example.rating.model.Establishment;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class TopEstablishmentsView {
    private JPanel panel;
    private JPanel postPanel;
    private List<Establishment> allEstablishments;
    private JComboBox<String> categoryFilter;
    private JTextField searchField;
    private EstablishmentController establishmentController;
    private LoginView loginView;

    public TopEstablishmentsView(EstablishmentController establishmentController) {
        panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        this.establishmentController = establishmentController;
        // Title Label
        panel.add(createTitleLabel(), BorderLayout.NORTH);

        // Filter Panel (Category, Search by Name)
        panel.add(createFilterPanel(), BorderLayout.SOUTH);

        // Establishments Panel
        postPanel = new JPanel();
        postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));
        postPanel.setBackground(Color.WHITE);
        postPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

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

    private JLabel createTitleLabel() {
        JLabel label = new JLabel("Top 10 Establishments", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(new Color(50, 50, 150));
        label.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        return label;
    }

    private JPanel createFilterPanel() {
        // Create a parent panel with a centered layout
        JPanel filterPanelWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        filterPanelWrapper.setBackground(Color.WHITE);

        // Create the actual filter panel with a horizontal layout for the fields
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        filterPanel.setBackground(Color.WHITE);

        // Category Filter
        categoryFilter = createCategoryFilter();
        categoryFilter.addActionListener(e -> updateList());

        // Search Field
        searchField = new JTextField(15);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.addActionListener(e -> updateList());

        // Add components to the filter panel
        filterPanel.add(new JLabel("Category:"));
        filterPanel.add(categoryFilter);
        filterPanel.add(new JLabel("Search:"));
        filterPanel.add(searchField);

        // Add the filter panel to the wrapper panel
        filterPanelWrapper.add(filterPanel);

        return filterPanelWrapper;
    }

    private JComboBox<String> createCategoryFilter() {
        JComboBox<String> categoryFilter = new JComboBox<>();
        categoryFilter.addItem("All Categories");
        categoryFilter.addItem("Restaurant");
        categoryFilter.addItem("Cafe");
        categoryFilter.addItem("Hotel");
        return categoryFilter;
    }

    public void updateList() {
        postPanel.removeAll();

        String selectedCategory = (String) categoryFilter.getSelectedItem();
        String searchText = searchField.getText().toLowerCase();

        List<Establishment> filteredEstablishments = filterEstablishments(selectedCategory, searchText);
        filteredEstablishments.sort((e1, e2) -> Double.compare(calculateLikePercentage(e2), calculateLikePercentage(e1)));

        // Create and add post panels
        filteredEstablishments.forEach(establishment -> {
            JPanel post = createPostPanel(establishment);
            postPanel.add(post);
            postPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Space between posts
        });

        postPanel.revalidate();
        postPanel.repaint();
    }

    private List<Establishment> filterEstablishments(String selectedCategory, String searchText) {
        return allEstablishments.stream()
                .filter(establishment -> selectedCategory.equals("All Categories") || establishment.getCategory().equalsIgnoreCase(selectedCategory))
                .filter(establishment -> establishment.getName().toLowerCase().contains(searchText))
                .collect(Collectors.toList());
    }

    private double calculateLikePercentage(Establishment establishment) {
        return (double) establishment.getLikes() / (establishment.getLikes() + establishment.getDislikes()) * 100;
    }

    private JPanel createPostPanel(Establishment establishment) {
        JPanel postPanel = new JPanel();
        postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));
        postPanel.setBackground(Color.WHITE);
        postPanel.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

        // Post Header: Establishment Name and Category
        postPanel.add(createHeaderPanel(establishment));

        // Post Image (if any) or placeholder
        postPanel.add(createImagePanel());

        // Post Content: Description and Stats
        postPanel.add(createContentPanel(establishment));

        // Post Footer: Likes, Dislikes, and Follow Button
        postPanel.add(createFooterPanel(establishment));

        return postPanel;
    }

    private JPanel createHeaderPanel(Establishment establishment) {
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(Color.WHITE);
        JLabel nameLabel = new JLabel(establishment.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel categoryLabel = new JLabel("• " + establishment.getCategory());
        categoryLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        categoryLabel.setForeground(new Color(100, 100, 100));

        headerPanel.add(nameLabel);
        headerPanel.add(categoryLabel);

        return headerPanel;
    }

    private JPanel createImagePanel() {
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon("path/to/thumb-up-icon.png"));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(Color.WHITE);
        imagePanel.add(imageLabel);

        return imagePanel;
    }

    private JPanel createContentPanel(Establishment establishment) {
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

        return contentPanel;
    }

    private JPanel createFooterPanel(Establishment establishment) {
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel likesPanel = createLikesPanel(establishment);
        footerPanel.add(createFavouriteButton(establishment));
        footerPanel.add(likesPanel);

        return footerPanel;
    }

    private JPanel createLikesPanel(Establishment establishment) {
        JPanel likesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        likesPanel.setBackground(Color.WHITE);
        likesPanel.add(new JLabel("Likes: " + establishment.getLikes()));
        likesPanel.add(new JLabel("Dislikes: " + establishment.getDislikes()));
        return likesPanel;
    }

    private JLabel createFavouriteButton(Establishment establishment) {
        JLabel heartIcon = new JLabel();
        ImageIcon outlineHeart = new ImageIcon(new ImageIcon("src/main/resources/com/example/rating/outline_heart")
                .getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));
        ImageIcon filledHeart = new ImageIcon(new ImageIcon("src/main/resources/com/example/rating/filled_heart")
                .getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));

        // Set the initial icon based on the establishment's favorite state
        heartIcon.setIcon(establishment.isFavourite() ? filledHeart : outlineHeart);
        heartIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        heartIcon.setToolTipText("Toggle Favourite");

        heartIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Toggle the favourite state
                boolean newFavouriteState = !establishment.isFavourite(); // Toggle the state

                // Save the new favourite state
                establishment.setFavourite(newFavouriteState); // Update establishment object state

                // Optionally, persist the new favourite state in the database
                // For example, you can use a controller to save this state to the database:
                // establishmentController.saveFavouriteState(establishment.getId(), newFavouriteState);

                // Update the button icon based on the new state
                if (newFavouriteState) {
                    establishmentController.addFavourite(establishment.getId());
                    heartIcon.setIcon(filledHeart);  // Set the icon to filled heart
                } else {
                    heartIcon.setIcon(outlineHeart);  // Set the icon to outlined heart
                }
            }
        });

        return heartIcon;
    }


}
