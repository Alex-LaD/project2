package com.example.projet2;

import com.example.projet2.sceneControllers.*;
import com.example.projet2.sceneControllers.UnusedDashboardController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import java.io.IOException;

public class SceneFactory {

    public static Scene create(SceneType type, Stage stage) {
        return switch (type) {
            case LOGIN -> new LoginController().buildScene();
            case SIGNUP -> new SignupController().buildScene();
            case DASHBOARD -> new DashboardController().buildScene();
            case TRANSACTION -> new TransactionController().buildScene();
        };
    }
}
