package com.example.projet2.repository;
import com.example.projet2.Transaction;
import com.example.projet2.database.DatabaseManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionRepositoryTest {

    @BeforeEach
    void setUp() {
        DatabaseManager.initializeDatabase();
        try (Connection conn = DatabaseManager.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM transactions");
            stmt.execute("DELETE FROM users");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testInsertAndGetTransactions() {
        UserRepository userRepo = new UserRepository();
        userRepo.insertUser(new com.example.projet2.User(0, "gabriel", "1234"));
        com.example.projet2.User user = userRepo.getUserByUsername("gabriel");
        Transaction t = new Transaction(
                0,
                user.getId(),
                50.0,
                "Food",
                "Lunch",
                LocalDate.now()
        );

        TransactionRepository.insertTransaction(t);
        List<Transaction> list = TransactionRepository.getTransactionsByUser(user.getId());
        assertEquals(1, list.size());
        assertEquals("Food", list.get(0).getCategory());
    }
}