package com.example.projet2.sceneControllers;

import com.example.projet2.*;
import com.example.projet2.repository.TransactionRepository;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;
import java.util.List;

public class DashboardController {

    @FXML private Label balanceLabel;
    @FXML private Label usernameLabel;
    @FXML private TableView<Transaction> transactionTable;
    @FXML private TableColumn<Transaction, Double>    colAmount;
    @FXML private TableColumn<Transaction, String>    colCategory;
    @FXML private TableColumn<Transaction, String>    colDescription;
    @FXML private TableColumn<Transaction, LocalDate> colDate;

    private final TransactionModel model = TransactionModel.getInstance();

    @FXML
    public void initialize() {
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        transactionTable.setItems(model.getTransactions());

        balanceLabel.textProperty().bind(
                model.balanceProperty().asString("Balance: $%.2f")
        );

        if (model.getCurrentUser() != null) {
            usernameLabel.setText("Welcome, " + model.getCurrentUser().getUsername());
        }
    }

    @FXML
    private void goToTransactionScene() {
        SceneManager.getInstance().navigateTo(SceneType.TRANSACTION);
    }

    @FXML
    private void handleDeleteTransaction() {
        Transaction selected = transactionTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a transaction to delete.");
            alert.showAndWait();
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText(null);
        confirm.setContentText("Delete \"" + selected.getDescription() + "\"?");
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                TransactionRepository.deleteTransaction(selected.getId());
                model.removeTransaction(selected);
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Deleted");
                success.setHeaderText(null);
                success.setContentText("Transaction deleted successfully.");
                success.showAndWait();
            }
        });
    }

}