package com.example.projet2.sceneControllers;

import com.example.projet2.*;
import com.example.projet2.repository.TransactionRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class TransactionController {

    @FXML
    private TextField amountField;

    @FXML
    private TextField categoryField;

    @FXML
    private TextField descriptionField;

    public Scene buildScene() {
        try {
            FXMLLoader loader = new FXMLLoader(SceneFactory.class.getResource("/com/example/projet2/transaction-view.fxml"));
            Parent root = loader.load();
            return new Scene(root, 600, 400);
        } catch (IOException e) {
            e.printStackTrace();
            return new Scene(new VBox(new Label("Error loading Transaction Scene")), 600, 400);
        }
    }

    @FXML
    public void initialize() {
        amountField.setText("");
        categoryField.setText("");
        descriptionField.setText("");
    }

    @FXML
    private void goBackToDashboard() {
        SceneManager.getInstance().navigateTo(SceneType.DASHBOARD);
    }

    @FXML
    private void addTransaction() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            String category = categoryField.getText();
            String description = descriptionField.getText();
            int userId = getCurrentUserId();
            Transaction transaction = new Transaction(
                    0,
                    userId,
                    amount,
                    category,
                    description,
                    java.time.LocalDate.now()
            );
            TransactionRepository.insertTransaction(transaction);
            System.out.println("Transaction added!");
            SceneManager.getInstance().navigateTo(SceneType.DASHBOARD);
            amountField.clear();
            categoryField.clear();
            descriptionField.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getCurrentUserId() {
        User currentUser = TransactionModel.getInstance().getCurrentUser();
        return currentUser != null ? currentUser.getId() : 1;
    }
}