package Bank;

import Accounts.Account;
import Accounts.CreditAccount;
import Accounts.SavingsAccount;
import Bank.Bank;

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


    // this tests the createNewSavingsAccount method/function
    @Test
    void testCreateNewSavingsAccount() {
        // Create a savings account
        SavingsAccount account = bank.createNewSavingsAccount();

        // The account should not be null
        assertNotNull(account);
        // The account should be added to the bank
        assertTrue(bank.getBankAccounts().contains(account));
    }


    // this tests the createNewCreditAccount method/function
    @Test
    void testCreateNewCreditAccount() {
        // Create a credit account
        CreditAccount account = bank.createNewCreditAccount();

        // The account should not be null
        assertNotNull(account);
        // The account should be added to the bank
        assertTrue(bank.getBankAccounts().contains(account));
    }


    // this will test the AddNewAccount method/function
    @Test
    void testAddNewAccount() {
        Account mockAccount = new SavingsAccount(bank, "12345", "John", "Doe", "john@example.com", "1234", 1000);
        bank.addNewAccount(mockAccount);

        // The bank should contain the new account
        assertTrue(bank.getBankAccounts().contains(mockAccount));
    }

    // this will test the AccountExists method/function
    @Test
    void testAccountExists() {
        Account mockAccount = new SavingsAccount(bank, "12345", "John", "Doe", "john@example.com", "1234", 1000);
        bank.addNewAccount(mockAccount);

        // The method should return true because the account was added
        assertTrue(Bank.accountExists(bank, "12345"));

        // It should return false if an account doesn't exist
        assertFalse(Bank.accountExists(bank, "99999"));
    }


    // this will test the GetBankAccount method/function
    @Test
    void testGetBankAccount() {
    }



    // this will test the GetBankAccountNotFounf method/function
    @Test
    void testGetBankAccountNotFound() {
    }
}
