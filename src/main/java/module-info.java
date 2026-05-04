module com.example.projet2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;

    opens com.example.projet2 to javafx.fxml;
    exports com.example.projet2;
    exports com.example.projet2.database;
    opens com.example.projet2.database to javafx.fxml;
    exports com.example.projet2.sceneControllers;
    opens com.example.projet2.sceneControllers to javafx.fxml;
}