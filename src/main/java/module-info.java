module com.example.manga {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.compiler;


    opens com.example.manga to javafx.fxml;
    exports com.example.manga;
}