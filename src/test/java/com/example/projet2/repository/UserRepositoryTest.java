package com.example.projet2.repository;
import com.example.projet2.User;
import com.example.projet2.database.DatabaseManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.Statement;
import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTest {

    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        DatabaseManager.initializeDatabase();
        userRepository = new UserRepository();
        try (Connection conn = DatabaseManager.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM users");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testInsertUserAndFindByUsername() {
        User user = new User(0, "gabriel", "1234");
        userRepository.insertUser(user);
        User result = userRepository.getUserByUsername("gabriel");
        assertNotNull(result);
        assertEquals("gabriel", result.getUsername());
        assertEquals("1234", result.getPassword());
    }

    @Test
    void testDeleteUser() {
        User user = new User(0, "gabriel", "1234");
        userRepository.insertUser(user);
        userRepository.deleteUserByUsername("gabriel");
        User result = userRepository.getUserByUsername("gabriel");
        assertNull(result);
    }

    @Test
    void testUpdateUserPassword() {
        User user = new User(0, "gabriel", "1234");
        userRepository.insertUser(user);
        userRepository.updatePassword("gabriel", "abcd");
        User updatedUser = userRepository.getUserByUsername("gabriel");
        assertNotNull(updatedUser);
        assertEquals("abcd", updatedUser.getPassword());
    }
}