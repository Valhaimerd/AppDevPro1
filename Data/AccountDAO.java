package Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO implements IAccountDAO {
    private final IDatabaseProvider databaseProvider;

    // Constructor Injection
    public AccountDAO(IDatabaseProvider databaseProvider) {
        this.databaseProvider = databaseProvider;
    }

    @Override
    public void addAccount(int bankId, String accountNumber, double balance, int accountTypeId) {
        String sql = "INSERT INTO Account (bank_id, account_number, balance, account_type) VALUES (?, ?, ?, ?)";
        try (Connection conn = databaseProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bankId);
            stmt.setString(2, accountNumber);
            stmt.setDouble(3, balance);
            stmt.setInt(4, accountTypeId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding account: " + e.getMessage());
        }
    }

    @Override
    public double getBalance(String accountNumber) {
        String sql = "SELECT balance FROM Account WHERE account_number = ?";
        try (Connection conn = databaseProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("balance");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving balance: " + e.getMessage());
        }
        return 0.0;
    }

    @Override
    public void updateBalance(String accountNumber, double newBalance) {
        String sql = "UPDATE Account SET balance = ? WHERE account_number = ?";
        try (Connection conn = databaseProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, newBalance);
            stmt.setString(2, accountNumber);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating balance: " + e.getMessage());
        }
    }
}
