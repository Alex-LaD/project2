package com.example.projet2;

import com.example.projet2.database.DatabaseManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        DatabaseManager.initializeDatabase();
        SceneManager.init(stage);
        stage.setTitle("Personal Budget Manager");
        SceneManager.getInstance().navigateTo(SceneType.LOGIN);
        stage.show();
    }
}
