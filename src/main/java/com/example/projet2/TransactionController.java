package com.example.projet2;

import com.example.projet2.repository.TransactionRepository;
import javafx.fxml.FXML;
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