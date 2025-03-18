package Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BankDAO implements IBankDAO {
    private final IDatabaseProvider databaseProvider;

    // Constructor Injection
    public BankDAO(IDatabaseProvider databaseProvider) {
        this.databaseProvider = databaseProvider;
    }

    @Override
    public void addBank(int bankId, String name, String passcode) {
        String sql = "INSERT INTO Bank (bank_id, name, passcode) VALUES (?, ?, ?)";

        try (Connection conn = databaseProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bankId);
            stmt.setString(2, name);
            stmt.setString(3, passcode);
            stmt.executeUpdate();

            System.out.println("Bank added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding bank: " + e.getMessage());
        }
    }


    @Override
    public void addBank(int bankId, String name, String passcode,
                        double depositLimit, double withdrawLimit,
                        double creditLimit, double processingFee) {
        String sql = "INSERT INTO Bank (bank_id, name, passcode, deposit_limit, withdraw_limit, credit_limit, processing_fee) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = databaseProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bankId);
            stmt.setString(2, name);
            stmt.setString(3, passcode);
            stmt.setDouble(4, depositLimit);
            stmt.setDouble(5, withdrawLimit);
            stmt.setDouble(6, creditLimit);
            stmt.setDouble(7, processingFee);
            stmt.executeUpdate();

            System.out.println("Bank added successfully with custom settings.");
        } catch (SQLException e) {
            System.out.println("Error adding bank: " + e.getMessage());
        }
    }

    @Override
    public List<String> getAllBanks() {
        List<String> banks = new ArrayList<>();
        String sql = "SELECT name FROM Bank";
        try (Connection conn = databaseProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                banks.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching banks: " + e.getMessage());
        }
        return banks;
    }
}
