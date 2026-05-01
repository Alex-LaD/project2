package com.example.projet2.sceneControllers;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class signupController {

    private static final int SCENE_WIDTH = 400;
    private static final int SCENE_HEIGHT = 300;

    public Scene buildScene() {
        // Add in username field and text
        Label username = new Label("Enter Username");
        TextField usernameField = new TextField();
        VBox usernameBox = new VBox(username,usernameField);

        // Add in password field and text
        Label password = new Label("Enter Password");
        PasswordField passwordField = new PasswordField();
        VBox passwordBox = new VBox(password,passwordField);

        // Add in confirm field and text
        Label confirm = new Label("Confirm Password");
        PasswordField confirmField = new PasswordField();
        VBox confirmBox = new VBox(confirm,confirmField);

        // Create signup button
        Button signup = new Button("Sign up");

        // Create return to login button
        Button rtrn = new Button("Return to login");

        // Store buttons in HBox
        HBox buttonBox = new HBox(signup,rtrn);

        // Store components in VBox
        VBox root = new VBox(usernameBox,passwordBox,confirmBox,buttonBox);

        // Create Scene with VBox as root
        Scene scene = new Scene(root,SCENE_WIDTH,SCENE_HEIGHT);

        // Handle logic for signup button
        signup.setOnAction(e -> {handleSignup(usernameField,passwordField,confirmField);});

        // Handle logic for return to login button
        rtrn.setOnAction(e -> {handleReturn();});

        // Return Scene
        return scene;
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
