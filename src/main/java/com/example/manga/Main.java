package com.example.manga;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MangaLibraryApp extends Application {
    @Override
    public void start(Stage stage) {
        MangaDAO mangaDAO = new MangaDAO();

        TextField titleField = new TextField();
        titleField.setPromptText("Title");

        TextField genreField = new TextField();
        genreField.setPromptText("Genre");

        TextField priceField = new TextField();
        priceField.setPromptText("Price");

        TextField stockField = new TextField();
        stockField.setPromptText("Stock");

        TextField ratingField = new TextField();
        ratingField.setPromptText("Rating");

        Button addButton = new Button("Add Manga");
        addButton.setOnAction(e -> {
            String title = titleField.getText();
            String genre = genreField.getText();
            double price = Double.parseDouble(priceField.getText());
            int stock = Integer.parseInt(stockField.getText());
            double rating = Double.parseDouble(ratingField.getText());
            mangaDAO.addManga(title, genre, price, stock, rating);
        });

        VBox root = new VBox(10, titleField, genreField, priceField, stockField, ratingField, addButton);
        Scene scene = new Scene(root, 400, 300);

        stage.setScene(scene);
        stage.setTitle("Manga Library Management");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
