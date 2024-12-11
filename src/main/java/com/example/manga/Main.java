package com.example.manga;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.StageStyle;
import javafx.concurrent.Task;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Splash Screen Setup
        Stage splashStage = new Stage(StageStyle.UNDECORATED);
        StackPane splashRoot = new StackPane(new Label("Loading, please wait..."));
        Scene splashScene = new Scene(splashRoot, 300, 200);
        splashStage.setScene(splashScene);
        splashStage.show();

        // Simulate loading task
        Task<Void> loadingTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(2000); // Simulate loading time
                return null;
            }
        };

        loadingTask.setOnSucceeded(event -> {
            // Close splash screen
            splashStage.close();

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
        });

        new Thread(loadingTask).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
