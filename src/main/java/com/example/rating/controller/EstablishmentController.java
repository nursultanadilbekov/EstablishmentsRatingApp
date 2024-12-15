package com.example.rating.controller;

import com.example.rating.view.View;
import com.example.rating.MangaService;
import com.example.rating.model.Model;
import java.util.List;

public class Controller {
    private final View view;
    private final MangaService service;

    public Controller(View view, MangaService service) {
        this.view = view;
        this.service = service;
        this.view.setMangaSelectionAction(this::loadChapters);
        loadMangaList();
    }

    private void loadMangaList() {
        List<Model> mangaList = service.getMangaList();
        view.updateMangaList(mangaList.stream().map(Model::getTitle).toList());
    }

    private void loadChapters(String mangaTitle) {
        Model selectedManga = service.getMangaList().stream()
                .filter(manga -> manga.getTitle().equals(mangaTitle))
                .findFirst()
                .orElse(null);

        if (selectedManga != null) {
            List<String> chapters = service.getChaptersForManga(selectedManga.getId());
            view.updateChapterList(selectedManga.getTitle(), chapters);
        }
    }
}
