package com.example.rating.model;

import java.time.LocalDateTime;

public class Rated {
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
    private boolean isLiked;
    private boolean isDisliked;
    private boolean isFavorite;
}
