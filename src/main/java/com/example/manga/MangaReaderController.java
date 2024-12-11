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

    void loadMangaList() {
        List<Manga> mangaList = model.getMangaList();
        if (mangaList.isEmpty()) {
            // Handle the case where the manga list is empty, perhaps with a warning
            System.out.println("Manga list is empty");
        }
        List<String> mangaTitles = mangaList.stream()
                .map(Manga::getTitle)
                .toList(); // Java 16+ method for immutable lists
        view.updateMangaList(mangaTitles);
    }


    private void loadChapters(String selectedMangaTitle) {
        Optional<Manga> selectedMangaOpt = model.getMangaList().stream()
                .filter(manga -> manga.getTitle().equals(selectedMangaTitle))
                .findFirst();

        selectedMangaOpt.ifPresent(selectedManga -> {
            List<String> chapters = model.getChaptersForManga(selectedManga.getId());
            if (chapters.isEmpty()) {
                System.out.println("No chapters available for " + selectedMangaTitle);
            }
            view.updateChapterList(selectedMangaTitle, chapters);
        });
    }

}
