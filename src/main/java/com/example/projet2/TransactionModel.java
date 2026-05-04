package com.example.projet2;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class TransactionModel {

    private static TransactionModel instance;

    private final ObservableList<Transaction> transactions =
            FXCollections.observableArrayList();

    private final ObjectProperty<User> currentUser = new SimpleObjectProperty<>();

    private final DoubleProperty balance = new SimpleDoubleProperty(0.0);
    private final ObjectProperty<Transaction> selectedTransaction = new SimpleObjectProperty<>();


    private TransactionModel() {
        transactions.addListener((javafx.collections.ListChangeListener<Transaction>) change -> {
            double sum = transactions.stream()
                    .mapToDouble(Transaction::getAmount)
                    .sum();
            balance.set(sum);
        });
    }

    public static TransactionModel getInstance() {
        if (instance == null) instance = new TransactionModel();
        return instance;
    }

    public ObservableList<Transaction> getTransactions() { return transactions; }

    public void addTransaction(Transaction t) { transactions.add(t); }

    public void removeTransaction(Transaction t) { transactions.remove(t); }

    public void updateTransaction(Transaction old, Transaction updated) {
        int idx = transactions.indexOf(old);
        if (idx >= 0) transactions.set(idx, updated);
    }

    public ObjectProperty<User> currentUserProperty() { return currentUser; }
    public User getCurrentUser() { return currentUser.get(); }
    public void setCurrentUser(User u) { currentUser.set(u); }

    public ReadOnlyDoubleProperty balanceProperty() { return balance; }
    public double getBalance() { return balance.get(); }
    public ObjectProperty<Transaction> selectedTransactionProperty() { return selectedTransaction; }
    public Transaction getSelectedTransaction() { return selectedTransaction.get(); }
    public void setSelectedTransaction(Transaction t) { selectedTransaction.set(t); }

}