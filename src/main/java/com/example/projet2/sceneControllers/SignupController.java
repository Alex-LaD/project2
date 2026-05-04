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

import java.util.ArrayList;
import java.util.Scanner;

public class SignupController {

    private static final String errorMessages = "Username already exists\n" +
                                                "Username needs to be at least 3 characters\n" +
                                                "Password needs to be at least 8 characters\n" +
                                                "Password needs at least one number\n" +
                                                "Password needs at least one special character\n" +
                                                "Password does not match confirm password";

    private final ArrayList<Label> errorList = new ArrayList<>();
    private final VBox errorBox = new VBox();

    private static final int SCENE_WIDTH = 400;
    private static final int SCENE_HEIGHT = 350;

    private static final int ROOT_SPACING = 15;
    private static final int BUTTON_SPACING = 40;

    public Scene buildScene() {

        generateErrors();
        errorBox.setAlignment(Pos.CENTER);

        // Add in username field and text
        Label username = new Label("Enter Username");
        TextField usernameField = new TextField();
        usernameField.setMaxWidth(SCENE_WIDTH * 0.5);
        VBox usernameBox = new VBox(username,usernameField);
        usernameBox.setAlignment(Pos.CENTER);

        // Add in password field and text
        Label password = new Label("Enter Password");
        PasswordField passwordField = new PasswordField();
        passwordField.setMaxWidth(SCENE_WIDTH * 0.5);
        VBox passwordBox = new VBox(password,passwordField);
        passwordBox.setAlignment(Pos.CENTER);

        // Add in confirm field and text
        Label confirm = new Label("Confirm Password");
        PasswordField confirmField = new PasswordField();
        confirmField.setMaxWidth(SCENE_WIDTH * 0.5);
        VBox confirmBox = new VBox(confirm,confirmField);
        confirmBox.setAlignment(Pos.CENTER);

        // Create signup button
        Button signup = new Button("Sign up");

        // Create return to login button
        Button rtrn = new Button("Return to login");

        // Store buttons in HBox
        HBox buttonBox = new HBox(signup,rtrn);
        buttonBox.setSpacing(BUTTON_SPACING);
        buttonBox.setAlignment(Pos.CENTER);

        // Store components in VBox
        VBox root = new VBox(usernameBox,passwordBox,confirmBox,errorBox,buttonBox);
        root.setSpacing(ROOT_SPACING);
        //root.setAlignment(Pos.CENTER);

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
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirm = confirmField.getText();
        boolean isValid = checkValidity(username, password, confirm);

        // if valid:
        if (isValid) {
            // Store new user in database
            UserRepository.insertUser(new User(67,username, password));

            // Get scene manager
            SceneManager sceneManager = SceneManager.getInstance();

            // uncache signup
            sceneManager.uncache(SceneType.SIGNUP);

            // navigate to login
            sceneManager.navigateTo(SceneType.LOGIN);
        }
    }

    private boolean checkValidity(String username, String password, String confirm) {
        boolean isValid = true;
        ArrayList<Boolean> failConditions = new ArrayList();

        // username must not exist in database
        failConditions.add(UserRepository.getUserByUsername(username) != null);
        // username must be at least 3 characters
        failConditions.add(username.replaceAll("\\s+", "").length() < 3);
        // password must be at least 8 characters
        failConditions.add(password.replaceAll("\\s+", "").length() < 8);
        // password must contain at least one digit
        failConditions.add(!password.matches("^.*\\d.*$"));
        // password must contain at least one special character
        failConditions.add(!password.matches("^.*([^a-zA-z0-9\\s]|_).*$"));
        // password and confirm must be equal
        failConditions.add(!password.equals(confirm));

        // throw exception if failConditions and errorList are not same size
        if (failConditions.size() != errorList.size()) {
            throw new RuntimeException("errorList and failConditions mismatch");
        }

        // Check each condition
        for (int pointer = 0 ; pointer < errorList.size() ; pointer++) {
            isValid = checkCondition(isValid, pointer, failConditions.get(pointer));
        }

        return isValid;
    }

    private boolean checkCondition(boolean isValid, int listPointer, boolean condition) {
        Label error = errorList.get(listPointer);
        if (condition) {
            // Invalid username/password
            isValid = false;

            // Show new error
            if (!errorBox.getChildren().contains(error)) {
                errorBox.getChildren().add(error);
            }
        } else {
            // Remove error message if it exists
            errorBox.getChildren().remove(error);
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
