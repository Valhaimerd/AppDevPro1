package Launchers;

import Accounts.IllegalAccountType;
import Accounts.SavingsAccount;
import Accounts.StudentAccount;
import Main.*;

/**
 * StudentAccountLauncher handles user interactions for Student Accounts.
 */
public class StudentAccountLauncher {

    private static StudentAccount loggedAccount;

    /**
     * Initializes the Student Account menu after login.
     */
    public static void studentAccountInit() throws IllegalAccountType {
        if (loggedAccount == null) {
            System.out.println("No account logged in.");
            return;
        }

        while (true) {
            Main.showMenuHeader("Student Account Menu");
            Main.showMenu(Menu.StudentAccountMenu.menuIdx);
            Main.setOption();

            switch (Main.getOption()) {
                case 1 -> System.out.println(loggedAccount.getAccountBalanceStatement());
                case 2 -> withdrawProcess();
                case 3 -> transferProcess();
                case 4 -> System.out.println(loggedAccount.getTransactionsInfo());
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    /**
     * Handles the withdrawal process with student restrictions.
     */
    public static void withdrawProcess() throws IllegalAccountType {
        Field<Double, Double> amountField = new Field<Double, Double>("Withdrawal Amount", Double.class, 1.0, new Field.DoubleFieldValidator());
        amountField.setFieldValue("Enter withdrawal amount: ");

        double amount = amountField.getFieldValue();
        if (loggedAccount.withdrawal(amount)) {
            System.out.println("✅ Withdrawal successful.");
        } else {
            System.out.println("❌ Withdrawal failed. Student withdrawal limit is $1000.");
        }
    }

    /**
     * Handles the fund transfer process for students.
     */
    public static void transferProcess() throws IllegalAccountType {
        Field<String, Integer> recipientField = new Field<String, Integer>("Recipient Account Number", String.class, 5, new Field.StringFieldLengthValidator());
        recipientField.setFieldValue("Enter recipient account number: ");
        String recipientAccountNum = recipientField.getFieldValue();

        Field<Double, Double> amountField = new Field<Double, Double>("Transfer Amount", Double.class, 1.0, new Field.DoubleFieldValidator());
        amountField.setFieldValue("Enter transfer amount: ");
        double amount = amountField.getFieldValue();

        SavingsAccount recipient = (SavingsAccount) loggedAccount.getBank().getBankAccount(recipientAccountNum);

        if (recipient == null) {
            System.out.println("❌ Recipient account not found.");
            return;
        }

        if (loggedAccount.transfer(recipient, amount)) {
            System.out.println("✅ Transfer successful.");
        } else {
            System.out.println("❌ Transfer failed. Students have a $1000 limit.");
        }
    }

    public static void setLoggedAccount(StudentAccount account) {
        loggedAccount = account;
    }

    public static StudentAccount getLoggedAccount() {
        return loggedAccount;
    }
}
