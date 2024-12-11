package com.example.manga;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Initialize the Service and View
        MangaService mangaService = new MangaService();
        MangaReaderView view = new MangaReaderView(primaryStage);

        // Initialize Controller with Model and View
        MangaReaderController controller = new MangaReaderController(view, mangaService);

        // Set initial manga list and show the primary stage
        controller.loadMangaList();

        // Set up basic window configuration
        primaryStage.setTitle("Manga Reader");
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
