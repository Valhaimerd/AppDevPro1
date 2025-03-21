package Services;

import Accounts.Account;
import Data.IAccountDAO;
import java.util.List;

public class AccountService {
    private final IAccountDAO accountDAO;

    public AccountService(IAccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    // Fetch all accounts from the DAO
    public List<Account> fetchAllAccounts() {
        return accountDAO.getAllAccounts();
    }

    public Account fetchAccountByNumber(String number) {
        return accountDAO.getDBAccountByNumber(number);
    }
    // Static fetch helper (optional, for use anywhere)
    public static List<Account> fetchAllAccountsStatic() {
        AccountService accountService = ServiceProvider.getAccountService();
        return accountService.fetchAllAccounts();
    }

    // Create an account with basic validation
    public void createAccount(int bankId, String accountNumber, double balance, int accountTypeId, String pin, String ownerFname, String ownerLname, String ownerEmail) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            System.out.println("Account number cannot be empty.");
            return;
        }
        if (balance < 0) {
            System.out.println("Initial balance cannot be negative.");
            return;
        }
        if (pin == null || pin.trim().isEmpty()) {
            System.out.println("PIN cannot be empty.");
            return;
        }

        accountDAO.addAccount(bankId, accountNumber, balance, accountTypeId, pin, ownerFname, ownerLname, ownerEmail);
        System.out.println("Account added successfully!");
    }

    public double getBalance(String accountNumber) {
        return accountDAO.getBalance(accountNumber);
    }

    // Method to update balance in the database
    public void updateBalance(Account account, Double amount) {
        accountDAO.updateBalance(account.getAccountNumber(), amount);
    }

    public void updateLoan(Account creditAccount, Double amount) {
        accountDAO.updateBalance(creditAccount.getAccountNumber(), amount);
    }
}
