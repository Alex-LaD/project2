package com.example.projet2.sceneControllers;

import com.example.projet2.SceneManager;
import com.example.projet2.SceneType;
import com.example.projet2.User;
import com.example.projet2.repository.UserRepository;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class LoginController {

    private Scene scene;
    private final Label errorMessage = new Label("Invalid Username or Password");

    // Window dimensions in pixels
    private static final int SCENE_WIDTH = 400;
    private static final int SCENE_HEIGHT = 300;

    private static final double BUTTON_SPACING = 40;
    private static final double ROOT_SPACING = 25;

    /**
     * Builds scene for login page.
     * @return The created scene
     */
    public Scene buildScene() {

        // Configure error message for invalid username/password
        errorMessage.setTextFill(Color.RED);
        errorMessage.setUnderline(true);
        errorMessage.setAlignment(Pos.CENTER);

        // Hide error message for now
        errorMessage.setVisible(false);

        // Create Username label and field;
        Label usernamePrompt = new Label("Enter Username");
        TextField usernameField = new TextField();
        usernameField.setMaxWidth(SCENE_WIDTH * 0.5);
        VBox usernameBox = new VBox(usernamePrompt, usernameField);
        usernameBox.setAlignment(Pos.CENTER);

        // Create password label and field
        Label passwordPrompt = new Label("Enter Password");
        PasswordField passwordField = new PasswordField();
        passwordField.setMaxWidth(SCENE_WIDTH * 0.5);
        VBox passwordBox = new VBox(passwordPrompt, passwordField);
        passwordBox.setAlignment(Pos.CENTER);

        // Create Buttons
        Button loginButton = new Button("Login");
        loginButton.setPrefWidth(SCENE_WIDTH * 0.25);
        loginButton.setStyle("-fx-background-color: #67ABFF; -fx-text-fill: white");
        Button signupButton = new Button("Signup");
        signupButton.setPrefWidth(SCENE_WIDTH * 0.15);

        // Add Buttons to HBox
        HBox buttonBox = new HBox(loginButton, signupButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(BUTTON_SPACING);

        // Add TextFields and HBox to VBox
        VBox root = new VBox(errorMessage, usernameBox, passwordBox, buttonBox);
        root.setSpacing(ROOT_SPACING);
        root.setAlignment(Pos.CENTER);

        // Add VBox to a new Scene
        scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

        // Logic for log in
        loginButton.setOnAction(e -> login(usernameField, passwordField));

        //logic for signup scene
        signupButton.setOnAction(e -> navigateToSignup());

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