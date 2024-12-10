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

    /**
     * Initializes the controller. Sets up the table columns and loads initial data.
     */
    @FXML
    public void initialize() {
        setupTableColumns();
        loadMangaData();
    }

    /**
     * Handles the action for adding a new manga to the library.
     */
    @FXML
    public void addManga() {
        try {
            String title = titleField.getText().trim();
            String genre = genreField.getText().trim();
            double price = parseDoubleField(priceField, "Price");
            int stock = parseIntegerField(stockField, "Stock");
            double rating = parseDoubleField(ratingField, "Rating");

            if (title.isEmpty() || genre.isEmpty()) {
                throw new IllegalArgumentException("Title and Genre cannot be empty.");
            }

            mangaDAO.addManga(title, genre, price, stock, rating);

            clearInputFields();
            loadMangaData();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Manga added successfully!");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", e.getMessage());
        } catch (IllegalArgumentException e) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", e.getMessage());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Could not add manga: " + e.getMessage());
        }
    }

    /**
     * Refreshes the table with the latest manga data from the database.
     */
    @FXML
    public void refreshTable() {
        loadMangaData();
    }

    /**
     * Configures the table columns for the Manga model properties.
     */
    private void setupTableColumns() {
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        genreColumn.setCellValueFactory(cellData -> cellData.getValue().genreProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        stockColumn.setCellValueFactory(cellData -> cellData.getValue().stockProperty().asObject());
        ratingColumn.setCellValueFactory(cellData -> cellData.getValue().ratingProperty().asObject());
    }

    /**
     * Loads manga data from the database into the table view.
     */
    private void loadMangaData() {
        mangaList.clear();
        try {
            mangaList.addAll(mangaDAO.getAllManga());
            mangaTable.setItems(mangaList);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Could not load manga data: " + e.getMessage());
        }
    }

    /**
     * Clears all input fields in the form.
     */
    private void clearInputFields() {
        titleField.clear();
        genreField.clear();
        priceField.clear();
        stockField.clear();
        ratingField.clear();
    }

    /**
     * Parses a text field into a double value, with error handling.
     *
     * @param field the TextField to parse
     * @param fieldName the name of the field (for error messages)
     * @return the parsed double value
     * @throws NumberFormatException if the field is not a valid double
     */
    private double parseDoubleField(TextField field, String fieldName) {
        try {
            return Double.parseDouble(field.getText().trim());
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid " + fieldName + " value. Please enter a valid number.");
        }
    }

    /**
     * Parses a text field into an integer value, with error handling.
     *
     * @param field the TextField to parse
     * @param fieldName the name of the field (for error messages)
     * @return the parsed integer value
     * @throws NumberFormatException if the field is not a valid integer
     */
    private int parseIntegerField(TextField field, String fieldName) {
        try {
            return Integer.parseInt(field.getText().trim());
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid " + fieldName + " value. Please enter a valid number.");
        }
    }

    /**
     * Displays an alert with the specified type, title, and content.
     *
     * @param alertType the type of the alert
     * @param title the title of the alert
     * @param content the content of the alert
     */
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
