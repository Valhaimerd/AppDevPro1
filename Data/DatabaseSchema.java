package Data;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

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
                + "pin TEXT NOT NULL, "
                + "owner_fname TEXT NOT NULL, "
                + "owner_lname TEXT NOT NULL, "
                + "owner_email TEXT NOT NULL, "
                + "FOREIGN KEY (account_type) REFERENCES AccountType(type_id), "
                + "FOREIGN KEY (bank_id) REFERENCES Bank(bank_id)"
                + ")";

        String transactionTable = "CREATE TABLE IF NOT EXISTS Transactions ("
                + "transaction_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "source_account TEXT NOT NULL, "
                + "target_account TEXT, "
                + "transaction_type TEXT NOT NULL, "
                + "amount REAL NOT NULL, "
                + "description TEXT, "
                + "date TEXT DEFAULT CURRENT_TIMESTAMP, "
                + "FOREIGN KEY (source_account) REFERENCES Account(account_number), "
                + "FOREIGN KEY (target_account) REFERENCES Account(account_number)"
                + ")";

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

    public static void clearTables() {
        IDatabaseProvider databaseProvider = new SQLiteDatabaseProvider();

        try (Connection conn = databaseProvider.getConnection();
             PreparedStatement clearTransactions = conn.prepareStatement("DELETE FROM Transactions");
             PreparedStatement clearAccounts = conn.prepareStatement("DELETE FROM Account");
             PreparedStatement clearBanks = conn.prepareStatement("DELETE FROM Bank")) {

            clearTransactions.executeUpdate();
            clearAccounts.executeUpdate();
            clearBanks.executeUpdate();

            System.out.println("All tables cleared successfully.");
        } catch (SQLException e) {
            System.out.println("Error clearing tables: " + e.getMessage());
        }
    }

    public static void clearAccountTypeTable() {
        IDatabaseProvider databaseProvider = new SQLiteDatabaseProvider();

        try (Connection conn = databaseProvider.getConnection();
             PreparedStatement clearAccountTypes = conn.prepareStatement("DELETE FROM AccountType")) {

            clearAccountTypes.executeUpdate();
            System.out.println("AccountType table cleared successfully.");
        } catch (SQLException e) {
            System.out.println("Error clearing AccountType table: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
//        clearAccountTypeTable();
//        clearTables();
//        createTables();
    }
}
