package Accounts;

import Bank.Bank;

/**
 * SavingsAccount class representing a standard savings account with balance tracking.
 * It allows deposits, withdrawals, and fund transfers while enforcing banking rules.
 */
public class SavingsAccount extends Account implements Withdrawal, Deposit, FundTransfer {

    private double balance;  // The current balance of the savings account

    /**
     * Constructor for SavingsAccount.
     *
     * @param bank        The bank associated with this savings account.
     * @param accountNumber The unique account number.
     * @param ownerFname Owner's first name.
     * @param ownerLname Owner's last name.
     * @param email       Owner's email address.
     * @param pin         Security PIN for authentication.
     * @param balance The initial deposit amount.
     * @throws IllegalArgumentException If the initial deposit is below 0.
     */
    public SavingsAccount(Bank bank, String accountNumber, String ownerFname, String ownerLname,
                          String email, String pin, double balance) {
        super(bank, accountNumber, ownerFname, ownerLname, email, pin);
        if (balance < 0) {
            throw new IllegalArgumentException("Initial deposit cannot be negative.");
        }
        this.balance = balance;
    }


    /**
     * Retrieves the account balance statement.
     *
     * @return The formatted balance statement.
     */
    public String getAccountBalanceStatement() {
        return "SavingsAccount{" +
                "Account Number='" + accountNumber + '\'' +
                ", Owner='" + ownerFname + " " + ownerLname + '\'' +
                ", Balance=$" + String.format("%.2f", balance) +
                '}';
    }

    /**
     * Checks if the account has enough balance for a transaction.
     *
     * @param amount The amount to check.
     * @return True if there is enough balance, false otherwise.
     */
    public boolean hasEnoughBalance(double amount) {
        return balance >= amount;
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
    public boolean cashDeposit(double amount) {
        if (amount <= 0 || amount > bank.getDepositLimit()) {
            return false; // Deposit cannot exceed bank deposit limit
        }

        // Adjust balance and log transaction
        adjustAccountBalance(amount);
        addNewTransaction(accountNumber, Transaction.Transactions.Deposit,
                "Deposited $" + String.format("%.2f", amount));

        return true;
    }

    /**
     * Withdraws an amount from this savings account.
     *
     * @param amount The amount to withdraw.
     * @return True if withdrawal is successful, false otherwise.
     */
    public boolean withdrawal(double amount) {
        if (amount <= 0 || amount > balance || amount > bank.getWithdrawLimit()) {
            insufficientBalance();
            return false; // Cannot withdraw more than available balance or withdrawal limit
        }

        // Adjust balance and log transaction
        adjustAccountBalance(-amount);
        addNewTransaction(accountNumber, Transaction.Transactions.Withdraw,
                "Withdrew $" + String.format("%.2f", amount));

        return true;
    }

    /**
     * Transfers funds to another SavingsAccount.
     * <p>
     * Internal transfers occur within the same bank, while external transfers include processing fees.
     *
     * @param recipient The recipient account.
     * @param amount    The amount to transfer.
     * @return True if the transfer was successful, false otherwise.
     * @throws IllegalArgumentException If attempting to transfer to a CreditAccount.
     */
    public boolean transfer(Account recipient, double amount) {
        if (!(recipient instanceof SavingsAccount)) {
            throw new IllegalArgumentException("Cannot transfer funds to a CreditAccount.");
        }

        if (!hasEnoughBalance(amount) || amount <= 0 || amount > bank.getWithdrawLimit()) {
            insufficientBalance();
            return false; // Insufficient funds or exceeding withdrawal limit
        }

        // Deduct from sender and add to recipient
        adjustAccountBalance(-amount);
        ((SavingsAccount) recipient).adjustAccountBalance(amount);

        // Log transactions for both accounts
        addNewTransaction(recipient.getAccountNumber(), Transaction.Transactions.FundTransfer,
                "Transferred $" + String.format("%.2f", amount) + " to " + recipient.getAccountNumber());
        recipient.addNewTransaction(accountNumber, Transaction.Transactions.FundTransfer,
                "Received $" + String.format("%.2f", amount) + " from " + accountNumber);

        return true;
    }

    /**
     * Transfers funds to another SavingsAccount from a different bank, applying a processing fee.
     *
     * @param recipientBank The recipient's bank.
     * @param recipient     The recipient account.
     * @param amount        The amount to transfer.
     * @return True if the transfer was successful, false otherwise.
     * @throws IllegalArgumentException If attempting to transfer to a CreditAccount.
     */
    public boolean transfer(Bank recipientBank, Account recipient, double amount) {
        if (!(recipient instanceof SavingsAccount)) {
            throw new IllegalArgumentException("Cannot transfer funds to a CreditAccount.");
        }

        double totalAmount = amount + bank.getProcessingFee(); // Ensure sender pays fee

        if (!hasEnoughBalance(totalAmount) || amount <= 0 || totalAmount > bank.getWithdrawLimit()) {
            insufficientBalance();
            return false; // Insufficient funds or exceeding withdrawal limit
        }

        // Deduct full amount from sender including processing fee
        adjustAccountBalance(-totalAmount);

        // Credit only the transferred amount (not including fee) to recipient
        ((SavingsAccount) recipient).adjustAccountBalance(amount);

        // Log transactions for both accounts
        addNewTransaction(recipient.getAccountNumber(), Transaction.Transactions.FundTransfer,
                "Transferred $" + String.format("%.2f", amount) + " to " + recipient.getAccountNumber() +
                        " at " + recipientBank.getBankName() + " (Fee: $" + bank.getProcessingFee() + ")");

        recipient.addNewTransaction(accountNumber, Transaction.Transactions.FundTransfer,
                "Received $" + String.format("%.2f", amount) + " from " + accountNumber +
                        " at " + bank.getBankName());

        return true;
    }

    /**
     * Retrieves the current account balance.
     *
     * @return The available balance.
     */
    public double getBalance() {
        return balance;
    }
}