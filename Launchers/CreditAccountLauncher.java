package Launchers;

import Accounts.Account;
import Accounts.CreditAccount;
import Accounts.IllegalAccountType;
import Accounts.SavingsAccount;
import Bank.*;
import Main.*;

/**
 * CreditAccountLauncher handles user interactions for Credit Accounts,
 * allowing credit payments and recompense.
 */
public class CreditAccountLauncher extends AccountLauncher {
    /**
     * Initializes the Credit Account menu after login.
     */
    public static void creditAccountInit() throws IllegalAccountType {
        if (!isLoggedIn()) {
            System.out.println("No account logged in.");
            return;
        }

        while (true) {
            Main.showMenuHeader("Credit Account Menu");
            Main.showMenu(41);
            Main.setOption();

            switch (Main.getOption()) {
                case 1 -> System.out.println(getLoggedAccount().getLoanStatement());
                case 2 -> creditPaymentProcess();
                case 3 -> creditRecompenseProcess();
                case 4 -> System.out.println(getLoggedAccount().getTransactionsInfo());
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    /**
     * Handles the credit payment process.
     */
    public static void creditPaymentProcess() throws IllegalAccountType {
        Field<String, Integer> recipientField = new Field<String, Integer>("Recipient Account Number", String.class, 5, new Field.StringFieldLengthValidator());
        recipientField.setFieldValue("Enter recipient Savings Account number: ");

        Field<Double, Double> amountField = new Field<Double, Double>("Payment Amount", Double.class, 1.0, new Field.DoubleFieldValidator());
        amountField.setFieldValue("Enter payment amount: ");

        String recipientAccountNum = recipientField.getFieldValue();
        double amount = amountField.getFieldValue();

        CreditAccount loggedAccount = getLoggedAccount();
        Bank recipientBank = loggedAccount.getBank();
        Account recipientAccount = recipientBank.getBankAccount(recipientAccountNum);

        if (!(recipientAccount instanceof SavingsAccount)) {
            System.out.println("Recipient account not found or is not a Savings Account.");
            return;
        }

        if (loggedAccount.pay(recipientAccount, amount)) {
            System.out.println("Credit payment successful.");
        } else {
            System.out.println("Credit payment failed. Insufficient funds or invalid amount.");
        }

    }

    /**
     * Handles the credit recompense process.
     */
    public static void creditRecompenseProcess() throws IllegalAccountType {
        Field<Double, Double> amountField = new Field<Double, Double>("Recompense Amount", Double.class, 1.0, new Field.DoubleFieldValidator());
        amountField.setFieldValue("Enter recompense amount: ");
        double amount = amountField.getFieldValue();

        CreditAccount loggedAccount = getLoggedAccount();
        if (loggedAccount.recompense(amount)) {
            System.out.println("Recompense successful.");
        } else {
            System.out.println("Recompense failed. Amount exceeds loan balance.");
        }
    }

    /**
     * Get the Credit Account instance of the currently logged account.
     * @return The currently logged account
     */
    protected static CreditAccount getLoggedAccount()
    {
        return (CreditAccount) AccountLauncher.getLoggedAccount();
    }
}