package com.example.rating.model;

import java.time.LocalDateTime;

public class Establishment {
    private int id;
    private int userId;
    private String name;
    private String address;
    private String description;
    private int likes;
    private int dislikes;
    private int categoryId; // Changed from category to categoryId
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructor
    public Establishment(int id, int userId, String name, String address, String description,
                         int likes, int dislikes, int categoryId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.address = address;
        this.description = description;
        this.likes = likes;
        this.dislikes = dislikes;
        this.categoryId = categoryId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters with validation
    public int getId() {
        return id;
    }



    public String getName() {
        return name;
    }
    public int getRating(){
        return likes;
    }
    

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        this.name = name;
        updateTimestamp();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be empty.");
        }
        this.address = address;
        updateTimestamp();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }
        this.description = description;
        updateTimestamp();
    }

    public int getLikes() {
        return likes;
    }

    public void incrementLikes() {
        incrementVotes(true);
    }

    public int getDislikes() {
        return dislikes;
    }

    public void incrementDislikes() {
        incrementVotes(false);
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        if (categoryId <= 0) {
            throw new IllegalArgumentException("Category ID cannot be less than or equal to zero.");
        }
        this.categoryId = categoryId;
        updateTimestamp();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // Method to calculate popularity score
    public double getPopularityScore() {
        int totalVotes = likes + dislikes;
        return totalVotes == 0 ? 0 : (double) likes / totalVotes * 100;
    }

    // Helper method to increment likes or dislikes
    private void incrementVotes(boolean isLike) {
        if (isLike) {
            this.likes++;
        } else {
            this.dislikes++;
        }
        updateTimestamp();
    }

    // Method to update timestamp when any field is updated
    private void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return String.format(
                "%s (%s) - Category ID: %d | Likes: %d, Dislikes: %d | Popularity: %.2f%%",
                name, address, categoryId, likes, dislikes, getPopularityScore()
        );
    }

    public String getCategory() {
        switch (categoryId) {
            case 1:
                return "Restaurant";
            case 2:
                return "Bar";
            case 3:
                return "Cafe";
            case 4:
                return "Hotel";
            default:
                return "Unknown Category";
        }
    }
    public int getLikesCount() {
        return likes;
    }

    public int getDislikesCount() {
        return dislikes;
    }
}
