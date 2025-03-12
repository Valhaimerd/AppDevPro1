package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseSeeder {
    private static final String DATABASE_PATH = "jdbc:sqlite:Data/banking_system.db";

    public static void insertSampleData() {
        String insertBank = "INSERT INTO Bank (name) VALUES (?)";
        String insertAccount = "INSERT INTO Account (bank_id, account_number, balance, account_type) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DATABASE_PATH);
             PreparedStatement bankStmt = conn.prepareStatement(insertBank);
             PreparedStatement accountStmt = conn.prepareStatement(insertAccount)) {

            // Insert Bank
            bankStmt.setString(1, "ABC Bank");
            bankStmt.executeUpdate();

            // Insert Account (Assuming Bank ID = 1)
            accountStmt.setInt(1, 1);
            accountStmt.setString(2, "1234567890");
            accountStmt.setDouble(3, 1000.00);
            accountStmt.setString(4, "Savings");
            accountStmt.executeUpdate();

            System.out.println("Sample data inserted successfully.");
        } catch (SQLException e) {
            System.out.println("Error inserting data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        insertSampleData();
    }
}