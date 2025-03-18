package Launchers;

import Accounts.BusinessAccount;
import Accounts.IllegalAccountType;
import Accounts.SavingsAccount;
import Main.*;

/**
 * BusinessAccountLauncher handles user interactions for Business Accounts.
 */
public class BusinessAccountLauncher {

    private static BusinessAccount loggedAccount;

    /**
     * Initializes the Business Account menu after login.
     */
    public static void businessAccountInit() throws IllegalAccountType {
        if (loggedAccount == null) {
            System.out.println("No account logged in.");
            return;
        }

        while (true) {
            Main.showMenuHeader("Business Account Menu");
            Main.showMenu(Menu.BusinessAccountMenu.menuIdx);
            Main.setOption();

            switch (Main.getOption()) {
                case 1 -> System.out.println(loggedAccount.getLoanStatement());
                case 2 -> businessPaymentProcess();
                case 3 -> recompenseProcess();
                case 4 -> System.out.println(loggedAccount.getTransactionsInfo());
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

        if (loggedAccount.recompense(amount)) {
            System.out.println("✅ Recompense successful.");
        } else {
            System.out.println("❌ Recompense failed. Amount exceeds loan balance.");
        }
    }

    public static void setLoggedAccount(BusinessAccount account) {
        loggedAccount = account;
    }

    public static BusinessAccount getLoggedAccount() {
        return loggedAccount;
    }
}
