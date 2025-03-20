package Accounts;

import java.util.ArrayList;
import java.util.List;

import Bank.Bank;
import Services.LogService;
import Services.Transaction;
import Services.ServiceProvider;

/**
 * Abstract Account class that serves as a base for different account types.
 * It includes personal details, basic account attributes, comparators, and transaction logging.
 */
public abstract class Account {

    // Core Attributes
    protected final Bank bank;
    protected final String accountNumber;
    protected final ArrayList<Transaction> transactions;

    // Owner Details
    protected final String ownerFname, ownerLname, ownerEmail;   // Owner's first name
    protected final String pin;          // 4-digit security PIN (hashed for security in real-world apps)

    public void loadTransactionsFromDatabase() {
        transactions.clear(); // Clear existing transactions
        LogService logService = ServiceProvider.getTransactionService();
        List<Transaction> allTransactions = logService.fetchTransactionsForAccount(this.accountNumber);
        transactions.addAll(allTransactions);
        System.out.println("Loaded " + transactions.size() + " transactions for account " + accountNumber + " from the database.");
    }

    /**
     * Constructor for an Account.
     *
     * @param bank          The bank associated with this account.
     * @param accountNumber The unique account number.
     * @param ownerFname    Owner's first name.
     * @param ownerLname    Owner's last name.
     * @param ownerEmail         Owner's email address.
     * @param pin           Security PIN (in real-world, store hashed PINs).
     */
    public Account(Bank bank, String accountNumber, String pin, String ownerFname,
                   String ownerLname, String ownerEmail) {
        this.bank = bank;
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.ownerFname = ownerFname;
        this.ownerLname = ownerLname;
        this.ownerEmail = ownerEmail;
        this.transactions = new ArrayList<>();
    }

    public String getOwnerFullName() {
        return this.ownerFname + " " + this.ownerLname;
    }

    /**
     * Adds a new transaction log to this account.
     *
     * @param sourceAccount The source account number that triggered this transaction.
     * @param type          The type of transaction performed.
     * @param description   A brief description of the transaction.
     */
    public void addNewTransaction(String sourceAccount, Transaction.Transactions type, String description) {
        transactions.add(new Transaction(sourceAccount, type, description));
    }

    /**
     * Retrieves all transaction logs recorded for this account.
     *
     * @return A formatted string containing all transaction details.
     */
    public String getTransactionsInfo() {
        if (transactions.isEmpty()) {
            return "No transactions found for this account.";
        }

        StringBuilder transactionLog = new StringBuilder("Transaction History:\n");
        for (Transaction transaction : transactions) {
            transactionLog.append(transaction.toString()).append("\n");
        }
        return transactionLog.toString();
    }

    public String getOwnerFname() {
        return ownerFname;
    }

    public String getOwnerLname() {
        return ownerLname;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public String getPin() {
        return pin;
    }

    public Bank getBank() {
        return bank;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Provides a string representation of the account details.
     *
     * @return Formatted account details.
     */
    @Override
    public String toString() {
        return String.format("Account Owner: %s %s (%s)\nBank: %s | Account No: %s | Transactions: %d",
                ownerFname, ownerLname, ownerEmail, bank.getName(), accountNumber, transactions.size());
    }
}