package com.example.manga;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.util.*;

public class MangaReaderView {
    private Stage stage;
    private ListView<String> mangaListView;
    private ListView<String> chaptersListView;
    private Button loadButton;

    // Constructor to initialize the UI
    public MangaReaderView(Stage stage) {
        this.stage = stage;
        stage.setTitle("Manga Reader");

        // Create ListViews for manga and chapters
        mangaListView = new ListView<>();
        chaptersListView = new ListView<>();

        // Load Button
        loadButton = new Button("Load Chapters");

        // Layout setup
        VBox layout = new VBox(10, new Label("Manga List:"), mangaListView, loadButton, new Label("Chapters:"), chaptersListView);
        Scene scene = new Scene(layout, 400, 400);
        stage.setScene(scene);
        stage.show();
    }

    // Methods to update the UI
    public void updateMangaList(List<String> mangaTitles) {
        mangaListView.getItems().clear();
        mangaListView.getItems().addAll(mangaTitles);
    }

    public void updateChapterList(List<String> chapters) {
        chaptersListView.getItems().clear();
        chaptersListView.getItems().addAll(chapters);
    }

    public void setLoadButtonAction(javafx.event.EventHandler<javafx.event.ActionEvent> handler) {
        loadButton.setOnAction(handler);
    }

    public int getSelectedMangaIndex() {
        return mangaListView.getSelectionModel().getSelectedIndex();
    }
}
