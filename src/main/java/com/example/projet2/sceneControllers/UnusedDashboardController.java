package com.example.projet2.sceneControllers;

import com.example.projet2.Transaction;
import com.example.projet2.TransactionModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class UnusedDashboardController {

    @FXML private Label balanceLabel;
    @FXML private Label usernameLabel;
    @FXML private TableView<Transaction> transactionTable;
    @FXML private TableColumn<Transaction, Double>  colAmount;
    @FXML private TableColumn<Transaction, String>  colCategory;
    @FXML private TableColumn<Transaction, String>  colDescription;
    @FXML private TableColumn<Transaction, java.time.LocalDate> colDate;

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

        usernameLabel.textProperty().bind(
                model.currentUserProperty().asString()
        );
    }

    @FXML
    private void handleAddTransaction() {
        Transaction t = new Transaction(
                0,
                model.getCurrentUser() != null ? model.getCurrentUser().getId() : 1,
                -50.0, "Food", "Groceries", java.time.LocalDate.now()
        );
        model.addTransaction(t);
    }

    @FXML
    private void handleDeleteTransaction() {
        Transaction selected = transactionTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            model.removeTransaction(selected);
        }
    }
}