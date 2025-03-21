package Launchers;

import Accounts.*;
import Bank.Bank;
import Main.*;
import Services.SecurityUtils;

/**
 * AccountLauncher handles user login and navigation to account-specific menus.
 */
public class AccountLauncher {

    private static Account loggedAccount;
    private static Bank assocBank;

    /**
     * Initializes the account login process.
     */
    public static void accountLogin() throws IllegalAccountType {
        if (BankLauncher.bankSize() == 0) {
            System.out.println("❌ No banks are available. Please create a bank first.");
            return;
        }

        assocBank = selectBank();
        if (assocBank == null) {
            System.out.println("❌ Invalid bank selection. Returning to main menu.");
            return;
        }

        Main.showMenuHeader("Select Account Type");
        Main.showMenu(Menu.AccountTypeSelection.menuIdx);
        Main.setOption();

        int accountTypeOption = Main.getOption();
        Class<? extends Account> accountType;

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

        String accountNumber = Main.prompt("Enter Account Number: ", false);
        String pin = Main.prompt("Enter 4-digit PIN: ", true);

        Account account = assocBank.getBankAccount(accountNumber);

        if (account == null) {
            System.out.println("❌ Account not found. Please try again.");
            return;
        }

        if (!account.getClass().equals(accountType)) {
            System.out.println("❌ Invalid account type. Please select the correct type.");
            return;
        }

        if (!checkCredentials(accountNumber, pin)) {
            System.out.println("❌ Invalid credentials. Login failed.");
            return;
        }

        setLogSession(account);
        System.out.println("✅ Login successful. Welcome, " + loggedAccount.getOwnerFname() + "!");
        System.out.println("Logged account type: " + loggedAccount.getClass().getName());

        if (loggedAccount.getClass().equals(SavingsAccount.class)) {
            AccountLauncher.setLogSession(loggedAccount);
            SavingsAccountLauncher.savingsAccountInit();
        } else if (loggedAccount.getClass().equals(CreditAccount.class)) {
            AccountLauncher.setLogSession(loggedAccount);
            CreditAccountLauncher.creditAccountInit();
        } else if (loggedAccount.getClass().equals(StudentAccount.class)) {
            AccountLauncher.setLogSession(loggedAccount);
            StudentAccountLauncher.studentAccountInit();
        } else if (loggedAccount.getClass().equals(BusinessAccount.class)) {
            AccountLauncher.setLogSession(loggedAccount);
            BusinessAccountLauncher.businessAccountInit();
        }

        destroyLogSession();
    }


    public static Bank selectBank() {
        if (BankLauncher.bankSize() == 0) {
            System.out.println("❌ No banks are available.");
            return null;
        } else {
            Main.showMenuHeader("Select a Bank by Name");
            BankLauncher.showBanksMenu();
            String inputBankName = Main.prompt("Enter Bank Name: ", false).trim().toLowerCase();
            Bank foundBank = null;

            for(Bank bank : BankLauncher.getBanks()) {
                if (bank.getName().trim().toLowerCase().equals(inputBankName)) {
                    foundBank = bank;
                    break;
                }
            }

            if (foundBank == null) {
                System.out.println("❌ Bank with name '" + inputBankName + "' not found.");
            }

            return foundBank;
        }
    }

    private static boolean checkCredentials(String accountNumber, String pin) {
        if (assocBank == null) {
            return false;
        } else {
            Account account = assocBank.getBankAccount(accountNumber.trim());
            if (account == null) {
                return false;
            } else {
                String hashedInputPin = SecurityUtils.hashCode(pin.trim());
                return account.getPin().equals(hashedInputPin);
            }
        }
    }

    /**
     * Creates a session for the logged-in account.
     *
     * @param account The account that successfully logged in.
     */
    protected static void setLogSession(Account account) {
        loggedAccount = account;
    }

    protected static Account getLoggedAccount() {
        return loggedAccount;
    }

    /**
     * Destroys the current account session.
     */
    protected static void destroyLogSession() {
        System.out.println("Logging out of " + loggedAccount.getAccountNumber());
        loggedAccount = null;
    }

    /**
     * Checks if an account is currently logged in.
     *
     * @return True if an account is logged in, false otherwise.
     */
    public static boolean isLoggedIn() {
        return loggedAccount != null;
    }
}