import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.io.*;
import java.sql.*;
import java.util.List;

class TransactionTest {
    private static final String TEST_CSV = "test_transactions.csv"; // CSV file for testing
    private Connection conn; // database connection for testing

@BeforeEach
void setUp() throws SQLException {
        // Runs before each test
        // Creates a temporary database in memory pero wala sya g save sa file
    conn = DriverManager.getConnection("jdbc:sqlite::memory:");
    try (Statement stmt = conn.createStatement()) {
        stmt.execute("CREATE TABLE Transactions (" +
                "accountNumber TEXT, " +
                "transactionType TEXT, " +
                "description TEXT)");
    }
}

}