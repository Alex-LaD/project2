package com.example.projet2;

import com.example.projet2.sceneControllers.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import java.io.IOException;

public class SceneFactory {

    private static Scene buildDashboardScene(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneFactory.class.getResource("/com/example/projet2/dashboard-view.fxml"));
            Parent root = loader.load();
            return new Scene(root);
        } catch (IOException e) {
            e.printStackTrace();
            return new Scene(new VBox(new Label("Error loading Dashboard")));
        }
    }

    static Scene buildTransactionScene(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneFactory.class.getResource("/com/example/projet2/transaction-view.fxml"));
            Parent root = loader.load();
            return new Scene(root, 600, 400);
        } catch (IOException e) {
            e.printStackTrace();
            return new Scene(new VBox(new Label("Error loading Transaction Scene")), 600, 400);
        }
    }

    public static Scene create(SceneType type, Stage stage) {
        return switch (type) {
            case LOGIN -> new LoginController().buildScene();
            case SIGNUP -> new SignupController().buildScene();
            case DASHBOARD -> buildDashboardScene(stage);
            case TRANSACTION -> buildTransactionScene(stage);
            case TRANSACTION_DETAIL -> buildFromFxml("transaction-detail-view.fxml", stage);
            case EDIT_TRANSACTION -> buildFromFxml("edit-transaction-view.fxml", stage);
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
