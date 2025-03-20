package Launchers;

import Accounts.*;
import Main.*;

/**
 * BusinessAccountLauncher handles user interactions for Business Accounts.
 */
public class BusinessAccountLauncher extends CreditAccountLauncher{
    /**
     * Initializes the Business Account menu after login.
     */
    public static void businessAccountInit() throws IllegalAccountType {
        if (!isLoggedIn()) {
            System.out.println("No account logged in.");
            return;
        }

        while (true) {
            Main.showMenuHeader("Business Account Menu");
            Main.showMenu(Menu.BusinessAccountMenu.menuIdx);
            Main.setOption();

            switch (Main.getOption()) {
                case 1 -> System.out.println(getLoggedAccount().getLoanStatement());
                case 2 -> businessPaymentProcess();
                case 3 -> recompenseProcess();
                case 4 -> System.out.println(getLoggedAccount().getTransactionsInfo());
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    /**
     * Handles the business payment process.
     */
    public static void businessPaymentProcess() throws IllegalAccountType {
        Field<String, Integer> recipientField = new Field<String, Integer>("Recipient Account Number", String.class, 5, new Field.StringFieldLengthValidator());
        recipientField.setFieldValue("Enter recipient Savings Account number: ");
        String recipientAccountNum = recipientField.getFieldValue();

        Field<Double, Double> amountField = new Field<Double, Double>("Payment Amount", Double.class, 1.0, new Field.DoubleFieldValidator());
        amountField.setFieldValue("Enter payment amount: ");
        double amount = amountField.getFieldValue();

        BusinessAccount loggedAccount = getLoggedAccount();
        SavingsAccount recipient = (SavingsAccount) loggedAccount.getBank().getBankAccount(recipientAccountNum);

        if (recipient == null) {
            System.out.println("❌ Recipient account not found.");
            return;
        }

        if (loggedAccount.pay(recipient, amount)) {
            System.out.println("✅ Payment successful.");
        } else {
            System.out.println("❌ Payment failed. Business credit limit exceeded.");
        }
    }

    /**
     * Handles the business recompense process.
     */
    public static void recompenseProcess() throws IllegalAccountType {
        Field<Double, Double> amountField = new Field<Double, Double>("Recompense Amount", Double.class, 1.0, new Field.DoubleFieldValidator());
        amountField.setFieldValue("Enter recompense amount: ");
        double amount = amountField.getFieldValue();

        CreditAccount loggedAccount = getLoggedAccount();
        if (loggedAccount.recompense(amount)) {
            System.out.println("✅ Recompense successful.");
        } else {
            System.out.println("❌ Recompense failed. Amount exceeds loan balance.");
        }
    }

    /**
     * Gets the currently logged-in Business Account.
     *
     * @return The logged-in BusinessAccount.
     */
    protected static BusinessAccount getLoggedAccount() {
        return (BusinessAccount) AccountLauncher.getLoggedAccount();
    }
}