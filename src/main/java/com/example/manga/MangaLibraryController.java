package com.example.manga;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MangaLibraryController {
    @FXML
    private TextField titleField;

    @FXML
    private TextField genreField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField stockField;

    @FXML
    private TextField ratingField;

    @FXML
    private Button addButton;

    @FXML
    private Button refreshButton;

    @FXML
    private TableView<Manga> mangaTable;

    @FXML
    private TableColumn<Manga, String> titleColumn;

    @FXML
    private TableColumn<Manga, String> genreColumn;

    @FXML
    private TableColumn<Manga, Double> priceColumn;

    @FXML
    private TableColumn<Manga, Integer> stockColumn;

    @FXML
    private TableColumn<Manga, Double> ratingColumn;

    private final MangaDAO mangaDAO = new MangaDAO();
    private final ObservableList<Manga> mangaList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialize table columns
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        genreColumn.setCellValueFactory(cellData -> cellData.getValue().genreProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        stockColumn.setCellValueFactory(cellData -> cellData.getValue().stockProperty().asObject());
        ratingColumn.setCellValueFactory(cellData -> cellData.getValue().ratingProperty().asObject());

        // Load data into the table
        loadMangaData();
    }

    @FXML
    public void addManga() {
        try {
            String title = titleField.getText();
            String genre = genreField.getText();
            double price = Double.parseDouble(priceField.getText());
            int stock = Integer.parseInt(stockField.getText());
            double rating = Double.parseDouble(ratingField.getText());

            // Add manga to the database
            mangaDAO.addManga(title, genre, price, stock, rating);

            // Clear input fields
            titleField.clear();
            genreField.clear();
            priceField.clear();
            stockField.clear();
            ratingField.clear();

            // Refresh table data
            loadMangaData();

            showAlert(Alert.AlertType.INFORMATION, "Success", "Manga added successfully!");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid input. Please check your data.");
        }
    }

    @FXML
    public void refreshTable() {
        loadMangaData();
    }

    private void loadMangaData() {
        mangaList.clear();
        mangaList.addAll(mangaDAO.getAllManga());
        mangaTable.setItems(mangaList);
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
