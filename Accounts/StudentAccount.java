package Accounts;

import Bank.Bank;
import Services.TransactionServices;

public class StudentAccount extends SavingsAccount {
    private final TransactionServices transactionService = new TransactionServices();
    public static final double MAX_WITHDRAWAL_LIMIT = 1000.00;

    /**
     * Constructor for StudentAccount.
     *
     * @param bank          The bank associated with this student account.
     * @param accountNumber The unique account number.
     * @param ownerFname    Owner's first name.
     * @param ownerLname    Owner's last name.
     * @param email         Owner's email address.
     * @param pin           Security PIN for authentication.
     * @param balance       The initial deposit amount.
     */
    public StudentAccount(Bank bank, String accountNumber, String ownerFname, String ownerLname,
                          String email, String pin, double balance) {
        super(bank, accountNumber, ownerFname, ownerLname, email, pin, balance);
    }

    /**
     * Withdraws an amount from this student account.
     *
     * @param amount The amount to withdraw.
     * @return True if withdrawal is successful, false otherwise.
     */
    @Override
    public synchronized boolean withdrawal(double amount) throws IllegalAccountType {
        // Let TransactionServices handle all validations and logic
        return transactionService.withdraw(this, amount);
    }

    /**
     * Transfers funds to another SavingsAccount (students may have restrictions on fund transfers).
     *
     * @param recipient The recipient account.
     * @param amount    The amount to transfer.
     * @return True if transfer is successful, false otherwise.
     */
    public synchronized boolean transfer(Account recipient, double amount) throws IllegalAccountType {
        if (amount <= 0 || amount > MAX_WITHDRAWAL_LIMIT) {
            System.out.println("Transaction failed: Exceeds student transfer limit.");
            return false;
        }

        return transactionService.transferFunds(this, recipient, amount);
    }

    @Override
    public String toString() {
        return "StudentAccount{" +
                "accountNumber='" + "ST" +  getAccountNumber() + '\'' +
                ", owner='" + getOwnerFname() + " " + getOwnerLname() + '\'' +
                ", balance=$" + String.format("%.2f", getAccountBalance()) +
                ", withdrawalLimit=$" + String.format("%.2f", MAX_WITHDRAWAL_LIMIT) +
                '}';
    }
}