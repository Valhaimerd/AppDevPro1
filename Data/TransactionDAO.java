package Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionDAO implements ITransactionDAO {
    private final IDatabaseProvider databaseProvider;

    public TransactionDAO(IDatabaseProvider databaseProvider) {
        this.databaseProvider = databaseProvider;
    }

    @Override
    public void logTransaction(String accountNumber, String type, double amount) {
        String sql = "INSERT INTO Transactions (account_number, transaction_type, amount) VALUES (?, ?, ?)";
        try (Connection conn = databaseProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accountNumber); // Changed from int to String
            stmt.setString(2, type);
            stmt.setDouble(3, amount);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error logging transaction: " + e.getMessage());
        }
    }
}
