package com.example.manga;

import com.example.manga.model.*;
import com.example.manga.view.*;
import com.example.manga.controller.*;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MangaReaderView view = new MangaReaderView();
            MangaService service = new MangaService();
            new MangaReaderController(view, service);
            view.setVisible(true);
        });
    }
}
