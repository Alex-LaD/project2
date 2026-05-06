package com.example.projet2.sceneControllers;

import com.example.projet2.*;
import com.example.projet2.*;
import com.example.projet2.repository.TransactionRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class DashboardController {

    @FXML private Label transactionCountLabel;
    @FXML private Label totalExpensesLabel;

    public Scene buildScene() {
        try {
            FXMLLoader loader = new FXMLLoader(SceneFactory.class.getResource("/com/example/projet2/dashboard-view.fxml"));
            Parent root = loader.load();
            return new Scene(root);
        } catch (IOException e) {
            e.printStackTrace();
            return new Scene(new VBox(new Label("Error loading Dashboard")));
        }
    }

    @FXML
    public void initialize() {
        refreshDashboard();
    }

    @FXML
    private void refreshDashboard() {
        int userId = getCurrentUserId();
        String currency = TransactionModel.getInstance().getCurrentUser().getCurrency();
        List<Transaction> transactions =
                TransactionRepository.getTransactionsByUser(userId);
        transactionCountLabel.setText("Transactions: " + transactions.size());
        double total = transactions.stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
        totalExpensesLabel.setText(String.format("Total expenses: %.2f %s", total, currency));
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

    @FXML
    private void goToConverterScene() {
        SceneManager.getInstance().navigateTo(SceneType.CONVERTER);
    }

    @FXML
    private void signOut() {
        SceneManager sceneManager = SceneManager.getInstance();
        sceneManager.uncacheAll();
        sceneManager.navigateTo(SceneType.LOGIN);
    }

    private int getCurrentUserId() {
        User currentUser = TransactionModel.getInstance().getCurrentUser();
        return currentUser != null ? currentUser.getId() : 1;
    }


}