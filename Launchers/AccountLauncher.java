package Launchers;

import Accounts.*;
import Bank.Bank;
import Main.*;

/**
 * AccountLauncher handles user login and navigation to account-specific menus.
 */
public class AccountLauncher {

    private Account loggedAccount;
    private Bank assocBank;

    public void setAssocBank(Bank assocBank) {
        this.assocBank = assocBank;
    }

    /**
     * Initializes the account login process.
     */
    public void accountLogin() throws IllegalAccountType {
        if (BankLauncher.bankSize() == 0) {
            System.out.println("❌ No banks are available. Please create a bank first.");
            return; // Prevents crashing, but allows user to go back
        }

        assocBank = selectBank();
        if (assocBank == null) {
            System.out.println("❌ Invalid bank selection. Returning to main menu.");
            return; // Avoid further execution when no bank is selected
        }

        // Prompt user to select account type
        Main.showMenuHeader("Select Account Type");
        Main.showMenu(Menu.AccountTypeSelection.menuIdx);
        Main.setOption();

        int accountTypeOption = Main.getOption();
        Class<? extends Account> accountType = null;

        switch (accountTypeOption) {
            case 1 -> accountType = CreditAccount.class;
            case 2 -> accountType = SavingsAccount.class;
            case 3 -> accountType = StudentAccount.class;
            case 4 -> accountType = BusinessAccount.class;
            case 0 -> {
                System.out.println("Returning to main menu.");
                return;
            }
            default -> {
                System.out.println("Invalid option.");
                return;
            }
        }

        // Prompt user for account number and PIN
        String accountNumber = Main.prompt("Enter Account Number: ", false);
        String pin = Main.prompt("Enter 4-digit PIN: ", true);

        // Retrieve account
        Account account = assocBank.getBankAccount(accountNumber);

        if (account == null) {
            System.out.println("❌ Account not found. Please try again.");
            return;
        }

        // Check if the account type matches
        if (!account.getClass().equals(accountType)) {
            System.out.println("❌ Invalid account type. Please select the correct type.");
            return;
        }

        // Check credentials
        if (!account.getPin().equals(pin)) {
            System.out.println("❌ Invalid credentials. Login failed.");
            return;
        }

        // Log in the user
        setLogSession(account);
        System.out.println("✅ Login successful. Welcome, " + loggedAccount.getOwnerFname() + "!");
        System.out.println("Logged account type: " + loggedAccount.getClass().getName());

        if (loggedAccount.getClass().equals(SavingsAccount.class)) {
            SavingsAccountLauncher.setLoggedAccount((SavingsAccount) loggedAccount);
            SavingsAccountLauncher.savingsAccountInit();
        } else if (loggedAccount.getClass().equals(CreditAccount.class)) {
            CreditAccountLauncher.setLoggedAccount((CreditAccount) loggedAccount);
            CreditAccountLauncher.creditAccountInit();
        } else if (loggedAccount.getClass().equals(StudentAccount.class)) {
            StudentAccountLauncher.setLoggedAccount((StudentAccount) loggedAccount);
            StudentAccountLauncher.studentAccountInit();
        } else if (loggedAccount.getClass().equals(BusinessAccount.class)) {
            BusinessAccountLauncher.setLoggedAccount((BusinessAccount) loggedAccount);
            BusinessAccountLauncher.businessAccountInit();
        }

        destroyLogSession();
    }


    /**
     * Allows the user to select a bank before logging into an account.
     *
     * @return The selected Bank instance.
     */
    public static Bank selectBank() {
        Main.showMenuHeader("Select a Bank");
        BankLauncher.showBanksMenu();
        if (BankLauncher.bankSize() == 0) return null;
        Main.setOption();

        int bankIndex = Main.getOption();
        return BankLauncher.getBankByIndex(bankIndex).orElse(null); // Unwrapping Optional
    }

    /**
     * Validates the login credentials.
     *
     * @param accountNumber The account number being verified.
     * @param pin           The entered PIN.
     * @return True if the credentials match, false otherwise.
     */
    private boolean checkCredentials(String accountNumber, String pin) {
        Account account = assocBank.getBankAccount(accountNumber);
        return account != null && account.getPin().equals(pin);
    }

    /**
     * Creates a session for the logged-in account.
     *
     * @param account The account that successfully logged in.
     */
    public void setLogSession(Account account) {
        this.loggedAccount = account;
    }

    /**
     * Destroys the current account session.
     */
    public void destroyLogSession() {
        System.out.println("Logging out of " + loggedAccount.getAccountNumber());
        loggedAccount = null;
    }

    /**
     * Checks if an account is currently logged in.
     *
     * @return True if an account is logged in, false otherwise.
     */
    public boolean isLoggedIn() {
        return loggedAccount != null;
    }
}