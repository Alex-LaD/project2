package com.example.projet2;

import com.example.projet2.sceneControllers.*;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneFactory {

    public static Scene create(SceneType type, Stage stage) {
        return switch (type) {
            case LOGIN -> new LoginController().buildScene();
            case SIGNUP -> new SignupController().buildScene();
            case DASHBOARD -> new DashboardController().buildScene();
            case TRANSACTION -> new TransactionController().buildScene();
            case CONVERTER -> new ConverterController().buildScene();
        };
    }
}
