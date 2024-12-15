package com.example.rating.model;

import java.time.LocalDateTime;

public class Establishment {
    private int id;
    private String name;
    private String address;
    private String description;
    private int likes;
    private int dislikes;
    private String category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Modify the constructor to accept LocalDateTime
    public Establishment(int id, String name, String address, String description, int likes, int dislikes,
                         String category, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.likes = likes;
        this.dislikes = dislikes;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Геттеры и сеттеры с валидацией
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название не может быть пустым.");
        }
        this.name = name;
        updateTimestamp();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Адрес не может быть пустым.");
        }
        this.address = address;
        updateTimestamp();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Описание не может быть пустым.");
        }
        this.description = description;
        updateTimestamp();
    }

    public int getLikes() {
        return likes;
    }

    public void incrementLikes() {
        this.likes++;
        updateTimestamp();
    }

    public int getDislikes() {
        return dislikes;
    }

    public void incrementDislikes() {
        this.dislikes++;
        updateTimestamp();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Категория не может быть пустой.");
        }
        this.category = category;
        updateTimestamp();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // Метод для расчёта популярности
    public double getPopularityScore() {
        int totalVotes = likes + dislikes;
        return totalVotes == 0 ? 0 : (double) likes / totalVotes * 100;
    }

    // Метод обновления времени последнего изменения
    private void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return String.format(
                "%s (%s) - Категория: %s | Лайков: %d, Дизлайков: %d | Популярность: %.2f%%",
                name, address, category, likes, dislikes, getPopularityScore()
        );
    }
}
