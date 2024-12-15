package com.example.manga.view;

import com.example.manga.controller.MangaReaderController;
import javafx.scene.control.Button;
import javafx.scene.control.VBox;
import javafx.scene.layout.Pane;

public class Sidebar {
    private VBox sidebar;

    public Sidebar(MangaReaderController controller) {
        sidebar = new VBox(10);

        Button allMangaButton = new Button("All Manga");
        allMangaButton.setOnAction(event -> controller.loadMangaList());

        Button addMangaButton = new Button("Add Manga");
        addMangaButton.setOnAction(event -> controller.showAddMangaForm());

        Button aboutButton = new Button("About");
        aboutButton.setOnAction(event -> controller.showAboutPage());

        sidebar.getChildren().addAll(allMangaButton, addMangaButton, aboutButton);
    }

    public Pane getView() {
        return sidebar;
    }
}
