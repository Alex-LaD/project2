package com.example.projet2.sceneControllers;

import com.example.projet2.SceneManager;
import com.example.projet2.SceneType;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LoginController {

    public Scene buildScene() {
        // Create TextFields;
        TextField usernameField = new TextField("username");
        TextField passwordField = new TextField("password");

        // Create Buttons
        Button login = new Button("Login");
        Button signup = new Button("Signup");

        // Add Buttons to HBox
        HBox hBox = new HBox(login, signup);
        // Add TextFields and HBox to VBox
        VBox vBox = new VBox(usernameField, passwordField, hBox);
        // Add VBox to a new Scene
        Scene scene = new Scene(vBox);

        // Logic for log in
        login.setOnAction(event -> {
            // Check if username and password match a user in database
            // uncache all
            // Navigate to dashboard scene
            // else
            // Show message, incorrect username or password
        });

        //logic for signup scene
        signup.setOnAction(e -> handleSignup(scene));

        // Return Scene
        return scene;
    }

    private void handleSignup(Scene scene) {
        SceneManager sceneManager = SceneManager.getInstance();
        // Cache login scene
        sceneManager.cache(SceneType.LOGIN, scene);
        // Navigate to signup scene
        sceneManager.navigateTo(SceneType.SIGNUP);
    }
}
