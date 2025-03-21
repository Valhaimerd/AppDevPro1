package Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Bank.Bank;

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
    public List<Bank> getAllBanksFull() {
        List<Bank> banks = new ArrayList<>();
        String sql = "SELECT * FROM Bank"; // Select all columns

        try (Connection conn = databaseProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Bank bank = new Bank(
                        rs.getInt("bank_id"),
                        rs.getString("name"),
                        rs.getString("passcode"),
                        rs.getDouble("deposit_limit"),
                        rs.getDouble("withdraw_limit"),
                        rs.getDouble("credit_limit"),
                        rs.getDouble("processing_fee")
                );
                banks.add(bank);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching banks: " + e.getMessage());
        }
        return banks;
    }
    @Override
    public Bank getDBBankByID(int id) {
        String sql = "SELECT * FROM Bank WHERE bank_id = ?";
        try (Connection conn = databaseProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Bank(
                        rs.getInt("bank_id"),
                        rs.getString("name"),
                        rs.getString("passcode"),
                        rs.getDouble("deposit_limit"),
                        rs.getDouble("withdraw_limit"),
                        rs.getDouble("credit_limit"),
                        rs.getDouble("processing_fee")
                );
            }
        } catch (SQLException e) {
            System.out.println("❌ Error fetching bank by ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Bank getDBBankByName(String name) {
        String sql = "SELECT * FROM Bank WHERE name = ?";
        try (Connection conn = databaseProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Bank(
                        rs.getInt("bank_id"),
                        rs.getString("name"),
                        rs.getString("passcode"),
                        rs.getDouble("deposit_limit"),
                        rs.getDouble("withdraw_limit"),
                        rs.getDouble("credit_limit"),
                        rs.getDouble("processing_fee")
                );
            }
        } catch (SQLException e) {
            System.out.println("❌ Error fetching bank by name: " + e.getMessage());
        }
        return null;
    }

}
