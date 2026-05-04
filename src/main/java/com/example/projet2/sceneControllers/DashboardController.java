package com.example.projet2.sceneControllers;

import com.example.projet2.*;
import com.example.projet2.repository.TransactionRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.util.List;

public class DashboardController {

    @FXML private Label transactionCountLabel;
    @FXML private Label totalExpensesLabel;

    private final TransactionModel model = TransactionModel.getInstance();

    @FXML
    public void initialize() {
        refreshDashboard();
    }

    @FXML
    private void refreshDashboard() {
        if (model.getCurrentUser() == null) return;
        List<Transaction> list = TransactionRepository.getTransactionsByUser(
                model.getCurrentUser().getId()
        );
        model.getTransactions().setAll(list);
        transactionCountLabel.setText("Transactions: " + list.size());
        double total = list.stream().mapToDouble(Transaction::getAmount).sum();
        totalExpensesLabel.setText(String.format("Total expenses: $%.2f", total));
    }

    @FXML
    private void goToTransactionScene() {
        SceneManager.getInstance().navigateTo(SceneType.TRANSACTION);
    }

    @FXML
    private void goToTransactionDetail() {
        SceneManager.getInstance().uncache(SceneType.TRANSACTION_DETAIL);
        SceneManager.getInstance().navigateTo(SceneType.TRANSACTION_DETAIL);
    }
}