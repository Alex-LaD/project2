package com.example.projet2;

import com.example.projet2.repository.TransactionRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class TransactionController {

    @FXML
    private TextField amountField;

    @FXML
    private TextField categoryField;

    @FXML
    private TextField descriptionField;

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
        String amountText  = amountField.getText().trim();
        String category    = categoryField.getText().trim();
        String description = descriptionField.getText().trim();

        if (amountText.isEmpty() || category.isEmpty() || description.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Fields");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountText);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Amount");
            alert.setHeaderText(null);
            alert.setContentText("Amount must be a valid number (e.g. -50.0 or 100.0).");
            alert.showAndWait();
            return;
        }

        int userId = TransactionModel.getInstance().getCurrentUser() != null
                ? TransactionModel.getInstance().getCurrentUser().getId() : 1;

        Transaction transaction = new Transaction(
                0, userId, amount, category, description, java.time.LocalDate.now()
        );

        TransactionRepository.insertTransaction(transaction);
        TransactionModel.getInstance().addTransaction(transaction);

        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setTitle("Success");
        success.setHeaderText(null);
        success.setContentText("Transaction added successfully!");
        success.showAndWait();

        amountField.clear();
        categoryField.clear();
        descriptionField.clear();

        SceneManager.getInstance().navigateTo(SceneType.DASHBOARD);
    }
    private int getCurrentUserId() {
        User currentUser = TransactionModel.getInstance().getCurrentUser();
        return currentUser != null ? currentUser.getId() : 1;
    }
}