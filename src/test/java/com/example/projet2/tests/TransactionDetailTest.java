package Tests;

import com.example.projet2.Transaction;
import com.example.projet2.TransactionModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionDetailTest {

    private TransactionModel model;

    @BeforeEach
    void setUp() {
        model = TransactionModel.getInstance();
        model.getTransactions().clear();
        model.setSelectedTransaction(null);
    }

    @Test
    void selectTransaction_shouldStoreInModel() {
        Transaction t = new Transaction(1, 1, 50.0, "Food", "Lunch", LocalDate.now());
        model.addTransaction(t);
        model.setSelectedTransaction(t);
        assertEquals(t, model.getSelectedTransaction());
    }

    @Test
    void deleteSelected_shouldRemoveFromList() {
        Transaction t = new Transaction(1, 1, 50.0, "Food", "Lunch", LocalDate.now());
        model.addTransaction(t);
        model.removeTransaction(t);
        assertTrue(model.getTransactions().isEmpty());
    }

    @Test
    void editTransaction_shouldUpdateFields() {
        Transaction t = new Transaction(1, 1, 50.0, "Food", "Lunch", LocalDate.now());
        model.addTransaction(t);
        model.setSelectedTransaction(t);
        t.setAmount(99.0);
        t.setCategory("Transport");
        t.setDescription("Bus");
        model.updateTransaction(t, t);
        Transaction result = model.getTransactions().get(0);
        assertEquals(99.0, result.getAmount(), 0.001);
        assertEquals("Transport", result.getCategory());
    }

    @Test
    void selectedTransaction_shouldBeNullByDefault() {
        assertNull(model.getSelectedTransaction());
    }
}