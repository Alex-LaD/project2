package com.example.projet2.sceneControllers;

import com.example.projet2.*;
import com.example.projet2.repository.TransactionRepository;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class EditTransactionController {

    @FXML private TextField amountField;
    @FXML private TextField categoryField;
    @FXML private TextField descriptionField;

    private final TransactionModel model = TransactionModel.getInstance();

    @FXML
    public void initialize() {
        Transaction t = model.getSelectedTransaction();
        if (t != null) {
            amountField.setText(String.valueOf(t.getAmount()));
            categoryField.setText(t.getCategory());
            descriptionField.setText(t.getDescription());
        }
    }

    @FXML
    private void handleSave() {
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
            alert.setContentText("Amount must be a valid number.");
            alert.showAndWait();
            return;
        }

        Transaction selected = model.getSelectedTransaction();
        selected.setAmount(amount);
        selected.setCategory(category);
        selected.setDescription(description);

        TransactionRepository.updateTransaction(selected);
        model.updateTransaction(selected, selected);

        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setTitle("Updated");
        success.setHeaderText(null);
        success.setContentText("Transaction updated successfully!");
        success.showAndWait();

        SceneManager.getInstance().navigateTo(SceneType.TRANSACTION_DETAIL);
    }

    @FXML
    private void handleCancel() {
        SceneManager.getInstance().navigateTo(SceneType.TRANSACTION_DETAIL);
    }
}