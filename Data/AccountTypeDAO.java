package Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class AccountTypeDAO {
    private final IDatabaseProvider databaseProvider;

    public AccountTypeDAO(IDatabaseProvider databaseProvider) {
        this.databaseProvider = databaseProvider;
    }

    public void insertAccountType(String typeName) {
        String sql = "INSERT INTO AccountType (type_name) VALUES (?) ON CONFLICT(type_name) DO NOTHING";
        try (Connection conn = databaseProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, typeName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error inserting account type: " + e.getMessage());
        }
    }

    public int getAccountTypeId(String typeName) {
        String sql = "SELECT type_id FROM AccountType WHERE type_name = ?";
        try (Connection conn = databaseProvider.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, typeName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("type_id");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching account type ID: " + e.getMessage());
        }
        return -1; // Return -1 if not found
    }


}
