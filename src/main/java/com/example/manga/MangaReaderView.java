package com.example.manga;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.util.List;

public class MangaReaderView {
    private Stage stage;
    private ListView<String> mangaListView;
    private ListView<String> chapterListView;
    private Button backButton;  // Button to go back to the manga list
    private MangaSelectionAction mangaSelectionAction;

    // Constructor
    public MangaReaderView(Stage stage) {
        this.stage = stage;

        // Set up ListView for manga list and chapter list
        mangaListView = new ListView<>();
        chapterListView = new ListView<>();
        backButton = new Button("Back to Manga List");

        // Set up layout using BorderPane
        BorderPane layout = new BorderPane();
        layout.setTop(backButton);  // Set back button at the top
        layout.setCenter(mangaListView);  // Initially show manga list

        // Create Scene
        Scene scene = new Scene(layout, 400, 600);
        stage.setTitle("Manga Reader");
        stage.setScene(scene);

        // Hide the back button initially
        backButton.setVisible(false);

        // Action when the back button is clicked
        backButton.setOnAction(event -> {
            // Simply call a method to show the manga list
            showMangaList();
        });

        // Action when a manga is double-clicked from the list
        mangaListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {  // Check if double-clicked
                String selectedMangaTitle = mangaListView.getSelectionModel().getSelectedItem();
                if (selectedMangaTitle != null && mangaSelectionAction != null) {
                    // Clear the manga list and load chapters for the selected manga
                    mangaListView.getItems().clear();  // Clear the manga list
                    mangaSelectionAction.onMangaSelected(selectedMangaTitle);  // Load chapters
                }
            }
        });
    }

    // Method to update the manga list in the view
    public void updateMangaList(List<String> mangaTitles) {
        mangaListView.getItems().setAll(mangaTitles);
        chapterListView.getItems().clear();
        backButton.setVisible(false);  // Hide the back button when displaying manga list
    }

    // Method to update the chapter list in the view
    public void updateChapterList(String mangaTitle, List<String> chapters) {
        chapterListView.getItems().setAll(chapters);

        // Show chapter list and back button
        BorderPane layout = (BorderPane) stage.getScene().getRoot();
        layout.setCenter(chapterListView);  // Display chapter list instead of manga list
        backButton.setVisible(true);  // Show the back button

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

    // Method to show the manga list again
    public void showMangaList() {
        BorderPane layout = (BorderPane) stage.getScene().getRoot();
        layout.setCenter(mangaListView);  // Set manga list back in the center
        backButton.setVisible(false);  // Hide the back button when we are back to manga list
    }

    // Functional interface for manga selection
    @FunctionalInterface
    public interface MangaSelectionAction {
        void onMangaSelected(String selectedMangaTitle);
    }
}
