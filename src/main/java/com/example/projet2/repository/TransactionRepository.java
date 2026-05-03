package com.example.projet2.repository;
import com.example.projet2.Transaction;
import com.example.projet2.database.DatabaseManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {

    public static void insertTransaction(Transaction t) {
        String sql = "INSERT INTO transactions(user_id, amount, category, description, date) VALUES(?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, t.getUserId());
            pstmt.setDouble(2, t.getAmount());
            pstmt.setString(3, t.getCategory());
            pstmt.setString(4, t.getDescription());
            pstmt.setString(5, t.getDate().toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Transaction> getTransactionsByUser(int userId) {
        String sql = "SELECT * FROM transactions WHERE user_id = ?";
        List<Transaction> list = new ArrayList<>();
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(new Transaction(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getDouble("amount"),
                        rs.getString("category"),
                        rs.getString("description"),
                        java.time.LocalDate.parse(rs.getString("date"))
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}