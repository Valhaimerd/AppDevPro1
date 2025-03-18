package Main;

import Accounts.*;
import Bank.Bank;

import java.util.Optional;

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

    public void accountLogin() {
        if (assocBank == null) {
            System.out.println("Bank selection failed. Please try again.");
            return;
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
            default -> {
                System.out.println("Invalid option. Returning to main menu.");
                return;
            }
        }

        // Prompt user for account number and PIN
        Field<String, Integer> accountField = new Field<String, Integer>("Account Number", String.class, 5, new Field.StringFieldLengthValidator());
        accountField.setFieldValue("Enter Account Number: ");

        Field<String, Integer> pinField = new Field<String, Integer>("4-digit PIN", String.class, 4, new Field.StringFieldLengthValidator());
        pinField.setFieldValue("Enter 4-digit PIN: ");

        String accountNumber = accountField.getFieldValue();
        String pin = pinField.getFieldValue();

        Class<? extends Account> finalAccountType = accountType;
        Optional<Account> account = assocBank.getBankAccount(accountNumber)
                .filter(acc -> acc.getClass().equals(finalAccountType)) // Ensure selected account type matches
                .filter(_ -> checkCredentials(accountNumber, pin));

        if (account.isPresent() && !isLoggedIn()) {
            setLogSession(account.get());
            System.out.println("Login successful. Welcome, " + loggedAccount.getOwnerFname() + "!");

            if (loggedAccount instanceof SavingsAccount) {
                SavingsAccountLauncher.setLoggedAccount((SavingsAccount) loggedAccount);
                SavingsAccountLauncher.savingsAccountInit();
            } else if (loggedAccount instanceof CreditAccount) {
                CreditAccountLauncher.setLoggedAccount((CreditAccount) loggedAccount);
                CreditAccountLauncher.creditAccountInit();
            }
            destroyLogSession();
        } else {
            System.out.println("Invalid credentials or account type mismatch. Login failed.");
        }
    }

    /**
     * Allows the user to select a bank before logging into an account.
     *
     * @return The selected Bank instance.
     */
    public static Bank selectBank() {
        if (BankLauncher.bankSize() == 0) {
            System.out.println("No banks are available. Please create a bank first.");
            return null;
        }

        Main.showMenuHeader("Select a Bank");
        BankLauncher.showBanksMenu();
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
        Optional<Account> account = assocBank.getBankAccount(accountNumber);
        return account.map(acc -> acc.getPin().equals(pin)).orElse(false);
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