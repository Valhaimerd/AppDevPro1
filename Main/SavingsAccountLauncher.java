package Main;

import java.util.Scanner;

import Accounts.Account;
import Accounts.IllegalAccountType;
import Accounts.SavingsAccount;

/**
 * SavingsAccountLauncher handles interactions with Savings Accounts,
 * including deposits, withdrawals, and fund transfers.
 */
public class SavingsAccountLauncher extends AccountLauncher {
    private static final Scanner scanner = new Scanner(System.in);
    /**
     * Initializes the savings account menu for user interactions.
     */
    public static void savingsAccountInit() {
        // TODO Complete this method.
        while (true) {
            System.out.println("\nSavings Account Menu:");
            System.out.println("1. Deposit Money");
            System.out.println("2. Withdraw Money");
            System.out.println("3. Transfer Funds");
            System.out.println("4. Logout");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    depositProcess();
                    break;
                case 2:
                    withdrawProcess();
                    break;
                case 3:
                    fundTransferProcess();
                    break;
                case 4:
                    System.out.println("Logging out...");
                    destroyLogSession();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    /**
     * Handles the deposit process for the logged-in Savings Account.
     */
    private static void depositProcess() {
        // TODO Complete this method.
        SavingsAccount loggedAccount = getLoggedAccount();
        if (loggedAccount == null) {
            System.out.println("No account is logged in.");
            return;
        }

        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();

        if (loggedAccount.cashDeposit(amount)) {
            System.out.println("Deposit successful.");
        } else {
            System.out.println("Deposit failed. Amount exceeds limit or is invalid.");
        }
    }

    /**
     * Handles the withdrawal process for the logged-in Savings Account.
     */
    private static void withdrawProcess() {
        // TODO Complete this method.
        SavingsAccount loggedAccount = getLoggedAccount();
        if (loggedAccount == null) {
            System.out.println("No account is logged in.");
            return;
        }

        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();

        if (loggedAccount.withdrawal(amount)) {
            System.out.println("Withdrawal successful.");
        } else {
            System.out.println("Withdrawal failed. Insufficient balance or exceeds limit.");
        }
    }

    /**
     * Handles the fund transfer process between Savings Accounts.
     */
    private static void fundTransferProcess() {
        // TODO Complete this method.
        SavingsAccount loggedAccount = getLoggedAccount();
        if (loggedAccount == null) {
            System.out.println("No account is logged in.");
            return;
        }

        System.out.print("Enter target Savings Account number: ");
        String targetAccountNum = scanner.nextLine();

        Account targetAccount = getAssocBank().getBankAccount(getAssocBank(), targetAccountNum);
        if (!(targetAccount instanceof SavingsAccount)) {
            System.out.println("Invalid target account. Must be a Savings Account.");
            return;
        }

        System.out.print("Enter amount to transfer: ");
        double amount = scanner.nextDouble();

        try {
            if (loggedAccount.transfer((SavingsAccount) targetAccount, amount)) {
                System.out.println("Fund transfer successful.");
            } else {
                System.out.println("Fund transfer failed. Insufficient balance or invalid amount.");
            }
        } catch (IllegalAccountType e) {
            System.out.println("Error: Transfer to a Credit Account is not allowed.");
        }
    }

    /**
     * Retrieves the logged-in Savings Account.
     * @return The SavingsAccount object if logged in, otherwise null.
     */
    protected static SavingsAccount getLoggedAccount() {
        // TODO Complete this method.
        if (loggedAccount instanceof SavingsAccount) {
            return (SavingsAccount) loggedAccount;
        }
        return null;
    }
}