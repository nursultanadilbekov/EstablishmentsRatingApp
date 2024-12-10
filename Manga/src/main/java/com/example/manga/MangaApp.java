package com.example.manga;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class MangaApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        MangaDAO mangaDAO = new MangaDAO();
        ListView<String> mangaListView = new ListView<>();
        Button loadButton = new Button("Load Manga Titles");
        Button addButton = new Button("Add New Manga");

        // Load Manga Titles
        loadButton.setOnAction(event -> {
            try {
                List<String> titles = mangaDAO.getAllMangaTitles();
                mangaListView.getItems().setAll(titles);
            } catch (SQLException e) {
                showError("Error loading manga titles: " + e.getMessage());
            }
        });

        // Add New Manga
        addButton.setOnAction(event -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Add Manga");
            dialog.setHeaderText("Enter Manga Title:");
            dialog.setContentText("Title:");

            dialog.showAndWait().ifPresent(title -> {
                try {
                    mangaDAO.addManga(title, "Unknown", "Unknown", null, 0);
                    showInfo("Manga added successfully!");
                } catch (SQLException e) {
                    showError("Error adding manga: " + e.getMessage());
                }
            });
        });

        VBox layout = new VBox(10, mangaListView, loadButton, addButton);
        layout.setPadding(new Insets(10));
        Scene scene = new Scene(layout, 400, 300);

        primaryStage.setTitle("Manga Library");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
