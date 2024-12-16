module com.example.rating {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.compiler;
    requires java.desktop;
    requires jbcrypt;
    requires org.slf4j;


    opens com.example.rating to javafx.fxml;
    exports com.example.rating;
    exports com.example.rating.view;
    opens com.example.rating.view to javafx.fxml;
    exports com.example.rating.controller;
    opens com.example.rating.controller to javafx.fxml;
    exports com.example.rating.model;
    opens com.example.rating.model to javafx.fxml;
}