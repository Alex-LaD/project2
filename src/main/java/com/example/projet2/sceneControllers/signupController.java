package com.example.projet2.sceneControllers;

import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class signupController {

    private static final int SCENE_WIDTH = 400;
    private static final int SCENE_HEIGHT = 300;

    public Scene buildScene() {
        // Add in username field and text
        // Add in password field and text
        // Add in confirm field and text

        // Create signup button

        // Create return to login button

        // Store buttons in HBox

        // Store components in VBox

        // Create Scene with VBox as root

        // Handle logic for signup button

        // Handle logic for return to login button

        // Return Scene
    }

    private void handleSignup(TextField usernameField, PasswordField passwordField, PasswordField confirmField) {
        // Check if valid username and password:
        // username does not exist in database
        // password is at least 8 characters
        // password contains at least one digit and at least one non-alphanumerical character
        // password and confirm are equal

        // if valid:
        // Store new user in database

        // else:
        // show errors
    }

    private void handleReturn() {
        // navigate to login scene
    }
}
