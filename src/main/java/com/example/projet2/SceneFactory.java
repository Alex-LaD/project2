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
        // Create TextFields;
        TextField usernameField = new TextField("username");
        TextField passwordField = new TextField("password");

        // Create Buttons
        Button login = new Button("Login");
        Button signup = new Button("Signup");

        // Logic for log in
        login.setOnAction(event -> {
            // Check if username and password match a user in database
                // uncache all
                // Navigate to dashboard scene
            // else
                // Show message, incorrect username or password
        });

        //logic for signup scene
        signup.setOnAction(event -> {
            // Cache login scene
            // Navigate to signup scene
        });

        // Add Buttons to HBox
        HBox hBox = new HBox(login, signup);
        // Add TextFields and HBox to VBox
        VBox vBox = new VBox(usernameField, passwordField, hBox);
        // Add VBox to a new Scene
        Scene scene = new Scene(vBox);
        // Return Scene
        return scene;
    }
    private static Scene buildSignupScene(Stage stage) { /* TODO */ return new Scene(new VBox());}
    private static Scene buildDashboardScene(Stage stage) { /* TODO */ return new Scene(new VBox());}
}
