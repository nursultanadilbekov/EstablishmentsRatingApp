package com.example.manga;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class MangaReaderView {
    private Stage stage;
    private ListView<String> mangaListView;
    private ListView<String> chapterListView;
    private Button backButton;
    private MangaSelectionAction mangaSelectionAction;
    private List<Manga> mangaList;

    public MangaReaderView(Stage stage) {
        this.stage = stage;

        mangaListView = new ListView<>();
        chapterListView = new ListView<>();
        backButton = new Button("Back to Manga List");

        BorderPane layout = new BorderPane();
        layout.setTop(backButton);
        layout.setCenter(mangaListView);

        Scene scene = new Scene(layout, 400, 600);
        stage.setTitle("Manga Reader");
        stage.setScene(scene);

        backButton.setVisible(false);

        backButton.setOnAction(event -> showMangaList());

        mangaListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                String selectedMangaTitle = mangaListView.getSelectionModel().getSelectedItem();
                if (selectedMangaTitle != null && mangaSelectionAction != null) {
                    mangaSelectionAction.onMangaSelected(selectedMangaTitle);
                }
            }
        });
    }

    public void updateMangaList(List<Manga> mangaList) {
        this.mangaList = mangaList;
        mangaListView.getItems().setAll(
                mangaList.stream().map(manga -> String.format("%s (%s)", manga.getTitle(), manga.getAuthor())).toList()
        );
        chapterListView.getItems().clear();
        backButton.setVisible(false);
    }

    public void updateChapterList(String mangaTitle, List<String> chapters) {
        chapterListView.getItems().setAll(chapters);

        BorderPane layout = (BorderPane) stage.getScene().getRoot();
        layout.setCenter(new VBox(
                new Text("Chapters for " + mangaTitle),
                chapterListView
        ));
        backButton.setVisible(true);

        if (chapters.isEmpty()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("No Chapters");
            alert.setHeaderText("No chapters available for " + mangaTitle);
            alert.showAndWait();
        }
    }

    public void showMangaList() {
        BorderPane layout = (BorderPane) stage.getScene().getRoot();
        layout.setCenter(mangaListView);
        backButton.setVisible(false);
    }

    public void setMangaSelectionAction(MangaSelectionAction action) {
        this.mangaSelectionAction = action;
    }

    @FunctionalInterface
    public interface MangaSelectionAction {
        void onMangaSelected(String selectedMangaTitle);
    }
}
