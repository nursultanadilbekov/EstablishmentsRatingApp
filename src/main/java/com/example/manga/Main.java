package com.example.manga;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Initialize the Model, View, and Controller
        MangaService mangaService = new MangaService();
        MangaReaderView view = new MangaReaderView(primaryStage);
        MangaReaderController controller = new MangaReaderController(view, mangaService);

        // Load the manga list at startup
        controller.loadMangaList();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
