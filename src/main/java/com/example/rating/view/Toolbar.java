package com.example.manga.view;

import com.example.manga.controller.MangaReaderController;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

public class Toolbar {
    private ToolBar toolbar;

    public Toolbar(MangaReaderController controller) {
        toolbar = new ToolBar();

        Button refreshButton = new Button("Refresh");
        refreshButton.setOnAction(event -> controller.loadMangaList());

        Button searchButton = new Button("Search");
        searchButton.setOnAction(event -> controller.showSearchDialog());

        toolbar.getItems().addAll(refreshButton, searchButton);
    }

    public ToolBar getView() {
        return toolbar;
    }
}
