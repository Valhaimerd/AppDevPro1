package Data;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSchema {
    public static void createTables() {
        String accountTypeTable = "CREATE TABLE IF NOT EXISTS AccountType ("
                + "type_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "type_name TEXT UNIQUE NOT NULL)";

        String bankTable = "CREATE TABLE IF NOT EXISTS Bank ("
                + "bank_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT NOT NULL, "
                + "passcode TEXT NOT NULL, " // New passcode column added
                + "deposit_limit REAL DEFAULT 50000, "
                + "withdraw_limit REAL DEFAULT 50000, "
                + "credit_limit REAL DEFAULT 100000, "
                + "processing_fee REAL DEFAULT 10)";

        String accountTable = "CREATE TABLE IF NOT EXISTS Account ("
                + "account_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "bank_id INTEGER, "
                + "account_number TEXT UNIQUE NOT NULL, "
                + "balance REAL DEFAULT 0, "
                + "account_type INTEGER, "
                + "FOREIGN KEY (account_type) REFERENCES AccountType(type_id), "
                + "FOREIGN KEY (bank_id) REFERENCES Bank(bank_id))";

        String transactionTable = "CREATE TABLE IF NOT EXISTS Transactions ("
                + "transaction_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "account_number TEXT NOT NULL, " // Changed from int to String
                + "transaction_type TEXT NOT NULL, "
                + "amount REAL NOT NULL, "
                + "date TEXT DEFAULT CURRENT_TIMESTAMP, "
                + "FOREIGN KEY (account_number) REFERENCES Account(account_number))";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(accountTypeTable);
            stmt.execute(bankTable);
            stmt.execute(accountTable);
            stmt.execute(transactionTable);
            System.out.println("Tables created successfully.");

        } catch (SQLException e) {
            System.out.println("Error creating tables: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        createTables();
    }
}
