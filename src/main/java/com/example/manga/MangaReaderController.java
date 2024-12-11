package com.example.manga;

import java.util.List;
import java.util.Optional;

public class MangaReaderController {
    private final MangaReaderView view;
    private final MangaService model;

    public MangaReaderController(MangaReaderView view, MangaService model) {
        this.view = view;
        this.model = model;
        this.view.setMangaSelectionAction(this::loadChapters);
        loadMangaList();
    }

    // Load manga list and pass titles to the view
    void loadMangaList() {
        List<Manga> mangaList = model.getMangaList();
        List<String> mangaTitles = mangaList.stream()
                .map(Manga::getTitle)
                .toList(); // Java 16+ method for immutable lists
        view.updateMangaList(mangaTitles);
    }

    // Load chapters for selected manga and update the view
    private void loadChapters(String selectedMangaTitle) {
        Optional<Manga> selectedMangaOpt = model.getMangaList().stream()
                .filter(manga -> manga.getTitle().equals(selectedMangaTitle))
                .findFirst();

        selectedMangaOpt.ifPresent(selectedManga -> {
            List<String> chapters = model.getChaptersForManga(selectedManga.getId());
            view.updateChapterList(selectedMangaTitle, chapters);
        });
    }
}
