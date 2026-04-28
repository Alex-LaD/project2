package com.example.projet2.sceneControllers;

import com.example.projet2.SceneManager;
import com.example.projet2.SceneType;
import com.example.projet2.User;
import com.example.projet2.repository.UserRepository;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class LoginController {

    private Scene scene;

    public Scene buildScene() {
        // Create TextFields;
        Text usernamePrompt = new Text("Enter Username");
        TextField usernameField = new TextField();
        Text passwordPrompt = new Text("Enter Password");
        TextField passwordField = new PasswordField();

        // Create Buttons
        Button login = new Button("Login");
        Button signup = new Button("Signup");

        // Add Buttons to HBox
        HBox hBox = new HBox(login, signup);
        // Add TextFields and HBox to VBox
        VBox vBox = new VBox(usernameField, passwordField, hBox);
        // Add VBox to a new Scene
        scene = new Scene(vBox);

        // Logic for log in
        login.setOnAction(e -> login(usernameField.getText(), passwordField.getText()));

        //logic for signup scene
        signup.setOnAction(e -> navigateToSignup(scene));

        // Return Scene
        return scene;
    }

    private void login(String username, String password) {

        UserRepository userRepository = new UserRepository();
        User user = userRepository.getUserByUsername(username);

        // Check if username and password match a user in database
        if (user != null && user.getPassword().equals(password)) {
            SceneManager sceneManager = SceneManager.getInstance();

            // uncache all
            sceneManager.uncacheAll();

            // Navigate to dashboard scene
            sceneManager.navigateTo(SceneType.DASHBOARD);
        }
        // else
        else {
            // Show message, incorrect username or password
            handleIncorrectUserInformation();
        }
    }

    private void navigateToSignup(Scene scene) {
        SceneManager sceneManager = SceneManager.getInstance();
        // Cache login scene
        sceneManager.cache(SceneType.LOGIN, scene);
        // Navigate to signup scene
        sceneManager.navigateTo(SceneType.SIGNUP);
    }

    private void handleIncorrectUserInformation() {

    }
}