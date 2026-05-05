package com.example.projet2;

import com.example.projet2.sceneControllers.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneFactory {

    public static Scene create(SceneType type, Stage stage) {
        return switch (type) {
            case LOGIN -> new LoginController().buildScene();
            case SIGNUP -> new SignupController().buildScene();
            case DASHBOARD -> new DashboardController().buildScene();
            case TRANSACTION -> new TransactionController().buildScene();
            case TRANSACTION_DETAIL -> buildFromFxml("transaction-detail-view.fxml", stage);
            case EDIT_TRANSACTION -> buildFromFxml("edit-transaction-view.fxml", stage);
            case CONVERTER -> new ConverterController().buildScene();
        };
    }

    private static Scene buildFromFxml(String fxmlFile, Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    SceneFactory.class.getResource("/com/example/projet2/" + fxmlFile)
            );
            Parent root = loader.load();
            return new Scene(root, 700, 500);
        } catch (IOException e) {
            e.printStackTrace();
            return new Scene(new VBox(new Label("Error loading " + fxmlFile)), 700, 500);
        }
    }
}
