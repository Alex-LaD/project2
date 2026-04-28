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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class LoginController {

    private Scene scene;
    private final Text errorMessage = new Text("Invalid Username or Password");

    /**
     * Builds scene for login page.
     * @return The created scene
     */
    public Scene buildScene() {

        // Create error message for invalid username/password
        errorMessage.setFill(Color.RED);
        errorMessage.setUnderline(true);

        // Hide error message for now
        errorMessage.setVisible(false);

        // Create Text and TextFields;
        Text usernamePrompt = new Text("Enter Username");
        TextField usernameField = new TextField();
        Text passwordPrompt = new Text("Enter Password");
        PasswordField passwordField = new PasswordField();

        // Create Buttons
        Button login = new Button("Login");
        Button signup = new Button("Signup");

        // Add Buttons to HBox
        HBox hBox = new HBox(login, signup);

        // Add TextFields and HBox to VBox
        VBox vBox = new VBox(errorMessage, usernamePrompt,
                             usernameField, passwordPrompt,
                             passwordField, hBox);

        // Add VBox to a new Scene
        scene = new Scene(vBox, 320, 240);

        // Logic for log in
        login.setOnAction(e -> login(usernameField, passwordField));

        //logic for signup scene
        signup.setOnAction(e -> navigateToSignup());

        // Return Scene
        return scene;
    }

    /**
     * Checks if username and password exist in database.
     * Navigates to dashboard if successful.
     * @param usernameField username input
     * @param passwordField password input
     */
    private void login(TextField usernameField, PasswordField passwordField) {

        UserRepository userRepository = new UserRepository();
        User user = userRepository.getUserByUsername(usernameField.getText());

        // Check if username and password match a user in database
        if (user != null && user.getPassword().equals(passwordField.getText())) {
            SceneManager sceneManager = SceneManager.getInstance();

            // uncache all
            sceneManager.uncacheAll();

            // Navigate to dashboard scene
            sceneManager.navigateTo(SceneType.DASHBOARD);
        }
        // else
        else {
            // Show message, incorrect username or password
            handleIncorrectUserInformation(usernameField, passwordField);
        }
    }

    /**
     * Navigates to sign in page
     */
    private void navigateToSignup() {

        // Hide error message
        errorMessage.setVisible(false);

        // Get Scene Manager
        SceneManager sceneManager = SceneManager.getInstance();

        // Cache login scene
        sceneManager.cache(SceneType.LOGIN, scene);

        // Navigate to signup scene
        sceneManager.navigateTo(SceneType.SIGNUP);
    }

    /**
     * Resets username and password fields, and display an error message.
     * For if user inputs invalid username or password.
     */
    private void handleIncorrectUserInformation(TextField usernameField, PasswordField passwordField) {
        // Reset text fields
        passwordField.clear();
        usernameField.clear();

        // Display error message
        errorMessage.setVisible(true);
    }
}