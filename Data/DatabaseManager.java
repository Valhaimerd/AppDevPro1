package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;



public class DatabaseManager {
    private static final String DATABASE_PATH = "jdbc:sqlite:Data/banking_system.db";

    public static void createDatabase() {
        try (Connection conn = DriverManager.getConnection(DATABASE_PATH)) {
            if (conn != null) {
                System.out.println("Database created successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        createDatabase();
    }
}