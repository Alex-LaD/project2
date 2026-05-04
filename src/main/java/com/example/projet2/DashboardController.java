package com.example.projet2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.example.projet2.repository.TransactionRepository;
import java.util.List;

public class DashboardController {

    @FXML
    private Label transactionCountLabel;

    @FXML
    private Label totalExpensesLabel;

    @FXML
    public void initialize() {
        refreshDashboard();
    }

    @FXML
    private void refreshDashboard() {
        int userId = getCurrentUserId();
        List<Transaction> transactions =
                TransactionRepository.getTransactionsByUser(userId);
        transactionCountLabel.setText("Transactions: " + transactions.size());
        double total = transactions.stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
        totalExpensesLabel.setText(String.format("Total expenses: $%.2f", total));
    }

    @FXML
    private void goToTransactionScene() {
        SceneManager.getInstance().navigateTo(SceneType.TRANSACTION);
    }

    private int getCurrentUserId() {
        User currentUser = TransactionModel.getInstance().getCurrentUser();
        if (currentUser == null) {
            throw new IllegalStateException("No user is currently logged in.");
        }
        return currentUser.getId();
    }
}