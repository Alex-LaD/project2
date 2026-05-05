package com.example.projet2.sceneControllers;

import com.example.projet2.*;
import com.example.projet2.repository.TransactionRepository;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;

public class TransactionDetailController {

    @FXML private TableView<Transaction> detailTable;
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

        detailTable.setItems(model.getTransactions());
    }

    @FXML
    private void handleEdit() {
        Transaction selected = detailTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a transaction to edit.");
            alert.showAndWait();
            return;
        }
        model.setSelectedTransaction(selected);
        SceneManager.getInstance().uncache(SceneType.EDIT_TRANSACTION);
        SceneManager.getInstance().navigateTo(SceneType.EDIT_TRANSACTION);
    }

    @FXML
    private void handleDelete() {
        Transaction selected = detailTable.getSelectionModel().getSelectedItem();
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

    @FXML
    private void handleBack() {
        SceneManager.getInstance().navigateTo(SceneType.DASHBOARD);
    }
}