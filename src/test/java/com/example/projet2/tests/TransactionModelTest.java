package com.example.projet2.tests;

import com.example.projet2.Transaction;
import com.example.projet2.TransactionModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionModelTest {

    private TransactionModel model;

    @BeforeEach
    void setUp() {
        model = TransactionModel.getInstance();
        model.getTransactions().clear();
    }

    @Test
    void addTransaction_shouldAppearInList() {
        Transaction t = new Transaction(1, 1, -50.0, "Food", "Groceries", LocalDate.now());
        model.addTransaction(t);
        assertEquals(1, model.getTransactions().size());
    }

    @Test
    void removeTransaction_shouldRemoveFromList() {
        Transaction t = new Transaction(1, 1, -50.0, "Food", "Groceries", LocalDate.now());
        model.addTransaction(t);
        model.removeTransaction(t);
        assertTrue(model.getTransactions().isEmpty());
    }

    @Test
    void balance_shouldUpdateWhenTransactionAdded() {
        model.addTransaction(new Transaction(1, 1, 100.0, "Income", "Salary", LocalDate.now()));
        model.addTransaction(new Transaction(2, 1, -30.0, "Food", "Lunch", LocalDate.now()));
        assertEquals(70.0, model.getBalance(), 0.001);
    }
}