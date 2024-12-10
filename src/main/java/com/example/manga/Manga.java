package com.example.manga;

public class Manga {
    private int id;
    private String title;
    private String genre;
    private String description;

    // Constructors, getters, and setters
    public Manga(int id, String title, String genre, String description) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public String getDescription() {
        return description;
    }
}
