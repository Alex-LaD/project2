module com.example.projet2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.projet2 to javafx.fxml;
    exports com.example.projet2;
}