package com.example.manga;

import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.util.List;

public class MangaReaderView {
    private Stage stage;
    private ListView<String> mangaListView;
    private ListView<String> chapterListView;
    private MangaSelectionAction mangaSelectionAction;

    // Constructor
    public MangaReaderView(Stage stage) {
        this.stage = stage;

        // Set up ListView for manga list and chapter list
        mangaListView = new ListView<>();
        chapterListView = new ListView<>();
        VBox layout = new VBox(10, mangaListView, chapterListView);

        // Create Scene
        Scene scene = new Scene(layout, 400, 600);
        stage.setTitle("Manga Reader");
        stage.setScene(scene);
    }

    // Method to update the manga list in the view
    public void updateMangaList(List<String> mangaTitles) {
        mangaListView.getItems().setAll(mangaTitles);

        // Action when a manga is double-clicked from the list
        mangaListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {  // Check if double-clicked
                String selectedMangaTitle = mangaListView.getSelectionModel().getSelectedItem();
                if (selectedMangaTitle != null && mangaSelectionAction != null) {
                    // Clear the manga list and load chapters for the selected manga
                    mangaListView.getItems().clear();  // Clear the manga list
                    mangaSelectionAction.onMangaSelected(selectedMangaTitle);  // Trigger the controller to load chapters
                }
            }
        });
    }

    // Method to update the chapter list in the view
    public void updateChapterList(String mangaTitle, List<String> chapters) {
        chapterListView.getItems().setAll(chapters);

        // Optional: display a message if there are no chapters
        if (chapters.isEmpty()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("No Chapters");
            alert.setHeaderText("No chapters available for " + mangaTitle);
            alert.showAndWait();
        }
    }

    // Method to set the manga selection action (passed from the controller)
    public void setMangaSelectionAction(MangaSelectionAction action) {
        this.mangaSelectionAction = action;
    }

    // Functional interface to pass the action to the controller
    @FunctionalInterface
    public interface MangaSelectionAction {
        void onMangaSelected(String selectedMangaTitle);
    }
}
