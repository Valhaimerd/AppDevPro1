import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import Accounts.Transaction;
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

@AfterEach
void tearDown() throws SQLException {
    // Runs after each test
    // Closes the database connection
    conn.close();

    // tig delete sa CSV file if ga exist
    File file = new File(TEST_CSV);
    if (file.exists()) {
        file.delete();
    }
}

@Test
void testTransactionCreation() {
    // Checks if a transaction is created correctly
    Transaction t = new Transaction("12345", Transaction.Transactions.Deposit, "Salary deposit");

    // Make sure the values match what we set
    assertEquals("12345", t.accountNumber);
    assertEquals(Transaction.Transactions.Deposit, t.transactionType);
    assertEquals("Salary deposit", t.description);
}

@Test
void testSaveAndLoadCSV() {
    // Creates a transaction and saves it to a CSV file
    Transaction t = new Transaction("67890", Transaction.Transactions.Withdraw, "ATM withdrawal");
    t.saveToCSV(TEST_CSV);
    
    // Loads transactions from the CSV file
    List<Transaction> transactions = Transaction.loadFromCSV(TEST_CSV);
    
    // Checks if the transaction was saved and loaded correctly
    assertFalse(transactions.isEmpty()); 
    assertEquals("67890", transactions.get(0).accountNumber);
    assertEquals(Transaction.Transactions.Withdraw, transactions.get(0).transactionType);
    assertEquals("ATM withdrawal", transactions.get(0).description);
}

@Test
void testSaveAndLoadDatabase() throws SQLException {
    // Creates a transaction and saves it to a CSV file
    Transaction t = new Transaction("67890", Transaction.Transactions.Withdraw, "ATM withdrawal");
    t.saveToCSV(TEST_CSV);

    // Loads transactions from the CSV file
    List<Transaction> transactions = Transaction.loadFromCSV(TEST_CSV);

    // Checks if the transaction was saved and loaded correctly
    assertFalse(transactions.isEmpty()); 
    assertEquals("67890", transactions.get(0).accountNumber);
    assertEquals(Transaction.Transactions.Withdraw, transactions.get(0).transactionType);
    assertEquals("ATM withdrawal", transactions.get(0).description);
}

@Test
void testInvalidTransactionType() {
    // Making sure nga if mag use ug invalid transaction type mag cause syga error
    assertThrows(IllegalArgumentException.class, () -> {
        Transaction.Transactions.valueOf("InvalidType"); // This should fail
    });
}

}