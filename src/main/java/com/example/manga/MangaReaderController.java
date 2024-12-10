package com.example.manga;

import javafx.event.ActionEvent;

import java.util.*;

public class MangaReaderController {
    private MangaReaderView view;
    private MangaService model;

    public MangaReaderController(MangaReaderView view, MangaService model) {
        this.view = view;
        this.model = model;

        // Set up the action for the Load button
        this.view.setLoadButtonAction(this::loadChapters);
    }

    // Method to load manga list
    public void loadMangaList() {
        List<Manga> mangaList = model.getMangaList();
        List<String> titles = new ArrayList<>();
        for (Manga manga : mangaList) {
            titles.add(manga.getTitle());
        }
        view.updateMangaList(titles);
    }

    // Method to load chapters for the selected manga
    public void loadChapters(ActionEvent event) {
        int selectedIndex = view.getSelectedMangaIndex();
        if (selectedIndex != -1) {
            List<Manga> mangaList = model.getMangaList();
            Manga selectedManga = mangaList.get(selectedIndex);
            List<String> chapters = model.getChaptersForManga(selectedManga.getId());
            view.updateChapterList(chapters);
        }
    }
}
