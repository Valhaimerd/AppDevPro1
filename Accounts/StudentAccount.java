package Accounts;

import Bank.Bank;
import Services.FundTransfer;
import Services.TransactionServices;
import Services.Withdrawal;

public class StudentAccount extends SavingsAccount implements FundTransfer, Withdrawal {
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
    public synchronized boolean withdrawal(double amount) {
        return transactionService.withdraw(this, amount);
    }

    /**
     * Transfers funds to another SavingsAccount (students may have restrictions on fund transfers).
     *
     * @param recipient The recipient account.
     * @param amount    The amount to transfer.
     * @return True if transfer is successful, false otherwise.
     */
    @Override
    public synchronized boolean transfer(Account recipient, double amount) throws IllegalAccountType {
        if (amount <= 0 || amount > MAX_WITHDRAWAL_LIMIT) {
            System.out.println("Transaction failed: Exceeds student transfer limit.");
            return false;
        }

        return transactionService.transfer(this, recipient, amount);
    }

    /**
     * Provides a string representation of the account details.
     *
     * @return Formatted account details.
     */
    @Override
    public String toString() {
        return String.format("Student Account | Owner: %s %s | Account No: ST%s | Balance: $%.2f | Max Withdrawal: $%.2f",
                getOwnerFname(), getOwnerLname(), getAccountNumber(), getAccountBalance(), MAX_WITHDRAWAL_LIMIT);
    }
}