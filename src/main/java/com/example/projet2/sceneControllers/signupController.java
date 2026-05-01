package com.example.projet2.sceneControllers;

import com.example.projet2.SceneManager;
import com.example.projet2.SceneType;
import com.example.projet2.repository.UserRepository;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Scanner;

public class signupController {

    private static final String errorMessages = "Username already exists\n" +
                                                "Password needs to be at least 8 characters\n" +
                                                "Password needs at least one number\n" +
                                                "Password needs at least one special character\n" +
                                                "Password does not match confirm password";

    private static final ArrayList<Label> errorList = new ArrayList<>();
    private static final VBox errorBox = new VBox();

    private static final int SCENE_WIDTH = 400;
    private static final int SCENE_HEIGHT = 300;

    public Scene buildScene() {

        generateErrors();

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
        VBox root = new VBox(usernameBox,passwordBox,confirmBox,errorBox,buttonBox);

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
        boolean isValid = checkValidity(usernameField.getText(), passwordField.getText(), confirmField.getText());

        // if valid:
            // Store new user in database

        // else:
            // show errors
    }

    private boolean checkValidity(String username, String password, String confirm) {
        boolean isValid = true;
        boolean[] failConditions = new boolean[errorList.size()];

        // username must not exist in database
        failConditions[0] = (UserRepository.getUserByUsername(username) != null);
        // password must be at least 8 characters
        failConditions[1] = (password.length() < 8);
        // password must contain at least one digit
        failConditions[2] = (!password.matches("^.*\\d.*$"));
        // password contains at least one special character
        failConditions[3] = (!password.matches("^.*^[a-zA-Z0-9].*$"));
        // password and confirm must be equal
        failConditions[4] = (password.equals(confirm));

        for (int pointer = 0 ; pointer < errorList.size() ; pointer++) {
            isValid = checkCondition(isValid, pointer, failConditions[pointer]);
        }

        return isValid;
    }

    private boolean checkCondition(boolean isValid, int listPointer, boolean condition) {
        if (condition) {
            // Invalid username/password
            isValid = false;

            // Show new error
            errorBox.getChildren().add(errorList.get(listPointer));
        } else {
            // Remove error message if it exists
            errorBox.getChildren().remove(errorList.get(listPointer));
        }
        return isValid;
    }

    private void handleReturn() {
        // navigate to login scene
        SceneManager.getInstance().navigateTo(SceneType.LOGIN);
    }

    private void generateErrors() {
        Scanner errors = new Scanner(errorMessages);
        while (errors.hasNextLine()) {
            Label error = new Label(errors.nextLine());
            error.setStyle("-fx-text-fill: red;");
            error.setUnderline(true);
            errorList.add(error);
        }
    }
}
