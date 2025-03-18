package Accounts;

import Bank.Bank;
import Services.*;

/**
 * SavingsAccount class representing a standard savings account with balance tracking.
 * It allows deposits, withdrawals, and fund transfers while enforcing banking rules.
 */
public class SavingsAccount extends Account {

    private double balance;  // The current balance of the savings account
    private final TransactionServices transactionService = new TransactionServices();

    /**
     * Constructor for SavingsAccount.
     *
     * @param bank        The bank associated with this savings account.
     * @param accountNumber The unique account number.
     * @param ownerFname Owner's first name.
     * @param ownerLname Owner's last name.
     * @param ownerEmail       Owner's email address.
     * @param pin         Security PIN for authentication.
     * @param balance The initial deposit amount.
     * @throws IllegalArgumentException If the initial deposit is below 0.
     */
    public SavingsAccount(Bank bank, String accountNumber, String pin, String ownerFname,
                          String ownerLname, String ownerEmail, double balance) {
        super(bank, accountNumber, pin, ownerFname, ownerLname, ownerEmail);
        if (balance < 0) {
            throw new IllegalArgumentException("Initial deposit cannot be negative.");
        }
        this.balance = balance;
    }

    /**
     * Checks if the account has enough balance for a transaction.
     *
     * @param amount The amount to check.
     * @return True if there is enough balance, false otherwise.
     */
    public boolean hasEnoughBalance(double amount) {
        return !(balance >= amount);
    }

    /**
     * Displays a warning when the account has insufficient balance.
     */
    public void insufficientBalance() {
        System.out.println("Warning: Insufficient balance to complete the transaction.");
    }

    /**
     * Adjusts the account balance of this savings account.
     *
     * @param amount The amount to adjust.
     */
    public void adjustAccountBalance(double amount) {
        this.balance += amount;
        if (this.balance < 0) {
            this.balance = 0; // Prevent negative balances
        }
    }

    /**
     * Deposits an amount into this savings account.
     *
     * @param amount The amount to deposit.
     * @return True if deposit is successful, false otherwise.
     */
    public synchronized boolean cashDeposit(double amount) throws IllegalAccountType {
        return transactionService.deposit(this, amount);
    }

    /**
     * Withdraws an amount from this savings account.
     *
     * @param amount The amount to withdraw.
     * @return True if withdrawal is successful, false otherwise.
     */
    public synchronized boolean withdrawal(double amount) throws IllegalAccountType {
        return transactionService.withdraw(this, amount);
    }

    /**
     * Transfers funds to another SavingsAccount.
     * <p>
     * Internal transfers occur within the same bank, while external transfers include processing fees.
     *
     * @param recipient The recipient account.
     * @param amount    The amount to transfer.
     * @return True if the transfer was successful, false otherwise.
     */
    public synchronized boolean transfer(Account recipient, double amount) throws IllegalAccountType {
        return transactionService.transferFunds(this, recipient, amount);
    }

    /**
     * Transfers funds to another SavingsAccount from a different bank, applying a processing fee.
     *
     * @param recipientBank The recipient's bank.
     * @param recipient     The recipient account.
     * @param amount        The amount to transfer.
     * @return True if the transfer was successful, false otherwise.
     * @throws IllegalAccountType If attempting to transfer to a CreditAccount.
     */
    public synchronized boolean transfer(Bank recipientBank, Account recipient, double amount) throws IllegalAccountType {
        return transactionService.transferFunds(this, recipientBank, recipient, amount);
    }

    public double getAccountBalance() {
        return this.balance;
    }

    /**
     * Retrieves the account balance statement.
     *
     * @return The formatted balance statement.
     */
    public String getAccountBalanceStatement() {
        return "SavingsAccount{" +
                "Account Number='" + "SA" + accountNumber + '\'' +
                ", Owner='" + ownerFname + " " + ownerLname + '\'' +
                ", Balance=$" + String.format("%.2f", balance) +
                '}';
    }
}