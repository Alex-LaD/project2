package com.example.projet2.repository;
import com.example.projet2.Category;
import com.example.projet2.database.DatabaseManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.Statement;
import static org.junit.jupiter.api.Assertions.*;

public class CategoryRepositoryTest {
    @BeforeEach
    void setUp() {
        DatabaseManager.initializeDatabase();
        try (Connection conn = DatabaseManager.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM categories");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testInsertAndFindCategory() {
        Category category = new Category(0, "Food");
        CategoryRepository.insertCategory(category);
        Category result = CategoryRepository.getCategoryByName("Food");
        assertNotNull(result);
        assertEquals("Food", result.getName());
    }

    @Test
    void testDeleteCategory() {
        Category category = new Category(0, "Food");
        CategoryRepository.insertCategory(category);
        CategoryRepository.deleteCategoryByName("Food");
        Category result = CategoryRepository.getCategoryByName("Food");
        assertNull(result);
    }
}