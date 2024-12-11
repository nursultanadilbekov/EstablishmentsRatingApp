package com.example.manga;

public class Manga {
    private int id;
    private String title;
    private String genre;
    private String status;
    private String author;
    private double rating;
    private String thumbnailURL;

    public Manga(int id, String title, String genre, String status, String author, double rating, String thumbnailURL) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.status = status;
        this.author = author;
        this.rating = rating;
        this.thumbnailURL = thumbnailURL;
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public String getStatus() { return status; }
    public String getAuthor() { return author; }
    public double getRating() { return rating; }
    public String getThumbnailURL() { return thumbnailURL; }

    @Override
    public String toString() {
        return String.format("Manga{id=%d, title='%s', genre='%s', status='%s', author='%s', rating=%.2f, thumbnailURL='%s'}",
                id, title, genre, status, author, rating, thumbnailURL);
    }
}