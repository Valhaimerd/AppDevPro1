package Accounts;

import java.util.Scanner;
import Bank.Bank;

/**
 * CreditAccountLauncher handles interactions with Credit Accounts,
 * including making payments and recompensing loans.
 */
public class CreditAccountLauncher extends AccountLauncher {
    private static final Scanner scanner = new Scanner(System.in);
    /**
     * Initializes the credit account menu for user interactions.
     */
    public static void creditAccountInit() {
        // TODO Complete this method.
        while (true) {
            System.out.println("\nCredit Account Menu:");
            System.out.println("1. Make a Credit Payment");
            System.out.println("2. Recompense Loan");
            System.out.println("3. Logout");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    creditPaymentProcess();
                    break;
                case 2:
                    creditRecompenseProcess();
                    break;
                case 3:
                    System.out.println("Logging out...");
                    destroyLogSession();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    /**
     * Processes a credit payment transaction to a Savings Account.
     */
    private static void creditPaymentProcess() {
        // TODO Complete this method.
        CreditAccount loggedAccount = getLoggedAccount();
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

        System.out.print("Enter amount to pay: ");
        double amount = scanner.nextDouble();

        try {
            if (loggedAccount.pay(targetAccount, amount)) {
                System.out.println("Payment successful.");
            } else {
                System.out.println("Payment failed. Ensure valid amount.");
            }
        } catch (IllegalAccountType e) {
            System.out.println("Error: Cannot make a payment to a Credit Account.");
        }
    }

    /**
     * Processes a recompense transaction to reduce loan balance.
     */
    private static void creditRecompenseProcess() {
        // TODO Complete this method.
        CreditAccount loggedAccount = getLoggedAccount();
        if (loggedAccount == null) {
            System.out.println("No account is logged in.");
            return;
        }

        System.out.print("Enter amount to recompense: ");
        double amount = scanner.nextDouble();

        if (loggedAccount.recompense(amount)) {
            System.out.println("Recompense successful. Loan reduced.");
        } else {
            System.out.println("Recompense failed. Amount exceeds loan balance.");
        }
    }

    /**
     * Retrieves the logged-in Credit Account.
     * @return The CreditAccount object if logged in, otherwise null.
     */
    protected static CreditAccount getLoggedAccount() {
        // TODO Complete this method.
        if (loggedAccount instanceof CreditAccount) {
            return (CreditAccount) loggedAccount;
        }
        return null;
    }
}
