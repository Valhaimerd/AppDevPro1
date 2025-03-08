package Main;

import Accounts.Account;
import Bank.Bank;
import java.util.Scanner;

public class AccountLauncher {
    protected static Account loggedAccount;
    private static Bank assocBank;
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Verifies if some account is currently logged in.
     * @return true if an account is logged in, false otherwise.
     */
    private static boolean isLoggedIn() {
        // TODO Complete this method.
        return loggedAccount != null;
    }

    public static Bank getAssocBank() {
        return this.assocBank;
    }

    public void setAssocBank(Bank assocBank) {
        this.assocBank = assocBank;
    }

    /**
     * Logs in an account. The user must select a bank before logging in.
     * Account existence will depend on the selected bank.
     */
    public static void accountLogin() {
        // TODO Complete this method.
        if (isLoggedIn()) {
            System.out.println("An account is already logged in. Please log out first.");
            return;
        }

        assocBank = selectBank();
        if (assocBank == null) {
            System.out.println("Invalid bank selection. Login aborted.");
            return;
        }

        System.out.print("Enter Account Number: ");
        String accountNum = scanner.nextLine();
        System.out.print("Enter 4-digit PIN: ");
        String pin = scanner.nextLine();

        Account account = checkCredentials(accountNum, pin);
        if (account != null) {
            setLogSession(account);
            System.out.println("Login successful! Welcome, " + accountNum);
        } else {
            System.out.println("Invalid credentials. Login failed.");
        }
    }

    /**
     * Prompts the user to select a bank before logging in.
     * @return the selected Bank object based on the entered Bank ID.
     */
    public static Bank selectBank() {
        // TODO Complete this method.
        System.out.println("Select Bank:");

        // Use the getter to access the list of banks
        for (Bank bank : BankLauncher.getBanks()) {
            System.out.println(bank.getID() + ": " + bank.getBankName());
        }

        System.out.print("Enter Bank ID: ");
        int bankID = scanner.nextInt();
        scanner.nextLine();

        // Find and return the Bank object
        for (Bank bank : BankLauncher.getBanks()) {
            if (bank.getID() == bankID) {
                return bank;
            }
        }

        System.out.println("Invalid Bank ID.");
        return null;
    }

    /**
     * Creates a login session based on the logged user account.
     * @param account the Account that has successfully logged in.
     */
    public static void setLogSession(Account account) {
        // TODO Complete this method.
        loggedAccount = account;
        assocBank = account.getBank();
    }

    /**
     * Destroys the log session of the previously logged user account.
     */
    public static void destroyLogSession() {
        // TODO Complete this method.
        if (!isLoggedIn()) {
            System.out.println("No account is currently logged in.");
            return;
        }

        System.out.println("Logging out " + loggedAccount.getACCOUNTNUMBER() + "...");
        loggedAccount = null;
        assocBank = null;
    }

    /**
     * Checks the inputted credentials during account login.
     * @param accountNum the account number.
     * @param pin the 4-digit PIN.
     * @return the Account object if the credentials pass verification, null otherwise.
     */
    public static Account checkCredentials(String accountNum, String pin) {
        // TODO Complete this method.
        Account account = assocBank.getBankAccount(assocBank, accountNum);
        if (account != null && account.getPin().equals(pin)) {
            return account;
        }
        return null;
    }

    /**
     * Retrieves the currently logged-in account.
     * @return the logged-in Account object.
     */
    protected static Account getLoggedAccount() {
        // TODO Complete this method.
        return loggedAccount;
    }
}