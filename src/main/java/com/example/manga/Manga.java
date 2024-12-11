package com.example.manga;

public class Manga {
    private int id;
    private String title;
    private String genre;
    private String status;

    public Manga(int id, String title, String genre, String status) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.status = status;
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public String getStatus() { return status; }

    @Override
    public String toString() {
        return String.format("Manga{id=%d, title='%s', genre='%s', status='%s'}", id, title, genre, status);
    }
}
