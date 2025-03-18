package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

//TODO RUN ONCE
//TODO purpose is to recreate tables if schema messed up badly

public class DatabaseSchema {
    private static final String DATABASE_PATH = "jdbc:sqlite:Data/banking_system.db";

    public static void createTables() {
        String bankTable = "CREATE TABLE IF NOT EXISTS Bank ("
                + "bank_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT NOT NULL, "
                + "deposit_limit REAL DEFAULT 50000, "
                + "withdraw_limit REAL DEFAULT 50000, "
                + "credit_limit REAL DEFAULT 100000, "
                + "processing_fee REAL DEFAULT 10)";

        String accountTable = "CREATE TABLE IF NOT EXISTS Account ("
                + "account_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "bank_id INTEGER, "
                + "account_number TEXT UNIQUE NOT NULL, "
                + "balance REAL DEFAULT 0, "
                + "account_type TEXT CHECK(account_type IN ('Savings', 'Credit')), "
                + "FOREIGN KEY (bank_id) REFERENCES Bank(bank_id))";

        String transactionTable = "CREATE TABLE IF NOT EXISTS Transactions ("
                + "transaction_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "account_id INTEGER, "
                + "transaction_type TEXT NOT NULL, "
                + "amount REAL NOT NULL, "
                + "date TEXT DEFAULT CURRENT_TIMESTAMP, "
                + "FOREIGN KEY (account_id) REFERENCES Account(account_id))";

        try (Connection conn = DriverManager.getConnection(DATABASE_PATH);
             Statement stmt = conn.createStatement()) {

            stmt.execute(bankTable);
            stmt.execute(accountTable);
            stmt.execute(transactionTable);
            System.out.println("Tables created successfully.");

        } catch (SQLException e) {
            System.out.println("Error creating tables: " + e.getMessage());
        }
    }
}