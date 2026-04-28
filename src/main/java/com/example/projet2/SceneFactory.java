package com.example.projet2;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class SceneFactory {

    public static Scene create(SceneType type, Stage stage) {
        return switch (type) {
            case LOGIN -> buildLoginScene(stage);
            case SIGNUP -> buildSignupScene(stage);
            case DASHBOARD -> buildDashboardScene(stage);
        };
    }

    private static Scene buildLoginScene(Stage stage) {

    }
    private static Scene buildSignupScene(Stage stage) { /* TODO */ return new Scene(new VBox());}
    private static Scene buildDashboardScene(Stage stage) { /* TODO */ return new Scene(new VBox());}
}
