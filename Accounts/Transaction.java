package Accounts;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class containing Transaction enums.
 */
public class Transaction {

    public enum Transactions {
        Deposit,
        Withdraw,
        FundTransfer,
        Payment,
        Recompense
    }

    /**
     * Account number that triggered this transaction.
     */
    public String accountNumber;
    /**
     * Type of transcation that was triggered.
     */
    public Transactions transactionType;
    /**
     * Description of the transaction.
     */
    public String description;

    public Transaction(String accountNumber, Transactions transactionType, String description) {
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.description = description;
    }

    /**
     * Saves transaction details to a CSV file.
     */
    public synchronized void saveToCSV(String filePath) {
        try (FileWriter fw = new FileWriter(filePath, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(accountNumber + "," + transactionType + "," + description);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads transactions from a CSV file.
     */
    public static List<Transaction> loadFromCSV(String filePath) {
        List<Transaction> transactions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                transactions.add(new Transaction(data[0], Transactions.valueOf(data[1]), data[2]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    /**
     * Saves transaction details to an SQLite database.
     */
    public synchronized void saveToDatabase(Connection conn) {
        String sql = "INSERT INTO Transactions (accountNumber, transactionType, description) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, accountNumber);
            pstmt.setString(2, transactionType.name());
            pstmt.setString(3, description);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads transactions from an SQLite database.
     */
    public static List<Transaction> loadFromDatabase(Connection conn) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT accountNumber, transactionType, description FROM Transactions";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                transactions.add(new Transaction(rs.getString("accountNumber"),
                        Transactions.valueOf(rs.getString("transactionType")),
                        rs.getString("description")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }
}