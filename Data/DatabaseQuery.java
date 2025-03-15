
package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;




/**
 * A class to interact with the SQLite database for fetching account information.
 */
public class DatabaseQuery {
    private static final String DATABASE_PATH = "jdbc:sqlite:Data/banking_system.db";

    public static void fetchAccounts() {
        String query = "SELECT a.account_number, a.balance, b.name AS bank_name "
                + "FROM Account a JOIN Bank b ON a.bank_id = b.bank_id";

        try (Connection conn = DriverManager.getConnection(DATABASE_PATH);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                System.out.println("Account: " + rs.getString("account_number") +
                        " | Balance: " + rs.getDouble("balance") +
                        " | Bank: " + rs.getString("bank_name"));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching accounts: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        fetchAccounts();
    }
}
