package Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Services.Transaction;

public class TransactionDAO implements ITransactionDAO {
    private final IDatabaseProvider databaseProvider;

    public TransactionDAO(IDatabaseProvider databaseProvider) {
        this.databaseProvider = databaseProvider;
    }

    @Override
    public void logTransaction(String sourceAccount, String targetAccount, String type, double amount, String description) {
        String sql = "INSERT INTO Transactions (source_account, target_account, transaction_type, amount, description) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = databaseProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sourceAccount);
            stmt.setString(2, targetAccount);
            stmt.setString(3, type);
            stmt.setDouble(4, amount);
            stmt.setString(5, description);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error logging transaction: " + e.getMessage());
        }
    }

    public List<Transaction> getTransactionsForAccount(String accountNumber) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM Transactions WHERE source_account = ? OR target_account = ?";

        try (Connection conn = databaseProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, accountNumber);
            stmt.setString(2, accountNumber);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Transaction transaction = new Transaction(
                        rs.getString("source_account"),
                        Transaction.Transactions.valueOf(rs.getString("transaction_type").toUpperCase()),
                        rs.getString("description")
                );
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error fetching transactions: " + e.getMessage());
        }

        return transactions;
    }
}
