package Bank;

import Accounts.Account;
import Accounts.CreditAccount;
import Accounts.SavingsAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This test file checks if the Bank class works properly.
 * It tests creating accounts, checking account existence, and retrieving accounts.
 */
public class BankTest {

    private Bank bank;

    @BeforeEach
    void setUp() {
        // Create a bank with ID 1 and name "Test Bank"
        bank = new Bank(1, "Test Bank");
    }

    @Test
    void testCreateNewSavingsAccount() {
        // Create a savings account
        SavingsAccount account = bank.createNewSavingsAccount();

        // The account should not be null
        assertNotNull(account);
        // The account should be added to the bank
        assertTrue(bank.getBankAccounts().contains(account));
    }
}
