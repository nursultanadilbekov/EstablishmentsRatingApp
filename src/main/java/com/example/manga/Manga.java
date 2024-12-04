package com.example.manga;

import javafx.beans.property.*;

public class Manga {
    private final StringProperty title;
    private final StringProperty genre;
    private final DoubleProperty price;
    private final IntegerProperty stock;
    private final DoubleProperty rating;

    public Manga(String title, String genre, double price, int stock, double rating) {
        this.title = new SimpleStringProperty(title);
        this.genre = new SimpleStringProperty(genre);
        this.price = new SimpleDoubleProperty(price);
        this.stock = new SimpleIntegerProperty(stock);
        this.rating = new SimpleDoubleProperty(rating);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public StringProperty genreProperty() {
        return genre;
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public IntegerProperty stockProperty() {
        return stock;
    }

    public DoubleProperty ratingProperty() {
        return rating;
    }
}
