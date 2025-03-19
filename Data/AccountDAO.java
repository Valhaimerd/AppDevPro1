package Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Accounts.*;  // Or wherever your Account class is located
import Bank.Bank;
import Launchers.BankLauncher;


public class AccountDAO implements IAccountDAO {
    private final IDatabaseProvider databaseProvider;

    // Constructor Injection
    public AccountDAO(IDatabaseProvider databaseProvider) {
        this.databaseProvider = databaseProvider;
    }

    @Override
    public void addAccount(int bankId, String accountNumber, double balance, int accountTypeId, String pin, String ownerFname, String ownerLname, String ownerEmail) {
        String sql = "INSERT INTO Account (bank_id, account_number, balance, account_type, pin, owner_fname, owner_lname, owner_email) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = databaseProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bankId);
            stmt.setString(2, accountNumber);
            stmt.setDouble(3, balance);
            stmt.setInt(4, accountTypeId);
            stmt.setString(5, pin);
            stmt.setString(6, ownerFname);
            stmt.setString(7, ownerLname);
            stmt.setString(8, ownerEmail);
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
            System.out.println("✅ Balance updated in database for account: " + accountNumber);
        } catch (SQLException e) {
            System.out.println("❌ Error updating account balance: " + e.getMessage());
        }
    }

    @Override
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT a.*, at.type_name, b.name as bank_name FROM Account a " +
                "JOIN AccountType at ON a.account_type = at.type_id " +
                "JOIN Bank b ON a.bank_id = b.bank_id";

        try (Connection conn = databaseProvider.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String bankName = rs.getString("bank_name");
                Bank bank = null;
                for (Bank b : BankLauncher.getBanks()) {
                    if (b.getName().equalsIgnoreCase(bankName)) {
                        bank = b;
                        break;
                    }
                }

                String typeName = rs.getString("type_name");
                Account account = switch (typeName) {
                    case "Savings" -> new SavingsAccount(
                            bank,
                            rs.getString("account_number"),
                            rs.getString("pin"),
                            rs.getString("owner_fname"),
                            rs.getString("owner_lname"),
                            rs.getString("owner_email"),
                            rs.getDouble("balance")
                    );
                    case "Credit" -> new CreditAccount(
                            bank,
                            rs.getString("account_number"),
                            rs.getString("pin"),
                            rs.getString("owner_fname"),
                            rs.getString("owner_lname"),
                            rs.getString("owner_email")
                    );
                    case "Business" -> new BusinessAccount(
                            bank,
                            rs.getString("account_number"),
                            rs.getString("pin"),
                            rs.getString("owner_fname"),
                            rs.getString("owner_lname"),
                            rs.getString("owner_email"),
                            rs.getDouble("balance")
                    );
                    case "Student" -> new StudentAccount(
                            bank,
                            rs.getString("account_number"),
                            rs.getString("pin"),
                            rs.getString("owner_fname"),
                            rs.getString("owner_lname"),
                            rs.getString("owner_email"),
                            rs.getDouble("balance")
                    );
                    default -> null;
                };

                if (account != null) {
                    accounts.add(account);
                } else {
                    System.out.println("Skipping unknown account type: " + typeName);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching accounts: " + e.getMessage());
        }

        return accounts;

    }


}
