package com.example.projet2;

import com.example.projet2.sceneControllers.*;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class SceneFactory {

    public static Scene create(SceneType type, Stage stage) {
        return switch (type) {
            case LOGIN -> new LoginController().buildScene();
            case SIGNUP -> new SignupController().buildScene();
            case DASHBOARD -> buildDashboardScene(stage);
        };
    }

    private static Scene buildDashboardScene(Stage stage) { /* TODO */ return new Scene(new VBox());}
}
