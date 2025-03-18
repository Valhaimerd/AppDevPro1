package Services;

import Data.ITransactionDAO;
import Data.IAccountDAO;

public class TransactionService {
    private final ITransactionDAO transactionDAO;
    private final IAccountDAO accountDAO;

    public TransactionService(ITransactionDAO transactionDAO, IAccountDAO accountDAO) {
        this.transactionDAO = transactionDAO;
        this.accountDAO = accountDAO;
    }

    public boolean deposit(String accountNumber, double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive.");
            return false;
        }

        double currentBalance = accountDAO.getBalance(accountNumber);
        accountDAO.updateBalance(accountNumber, currentBalance + amount);
        transactionDAO.logTransaction(accountNumber, "DEPOSIT", amount);
        System.out.println("Deposit successful!");
        return true;
    }

    public boolean withdraw(String accountNumber, double amount) {
        double currentBalance = accountDAO.getBalance(accountNumber);

        if (amount <= 0 || currentBalance < amount) {
            System.out.println("Insufficient funds or invalid amount.");
            return false;
        }

        accountDAO.updateBalance(accountNumber, currentBalance - amount);
        transactionDAO.logTransaction(accountNumber, "WITHDRAWAL", amount); // ✅ Now accepts String
        System.out.println("Withdrawal successful!");
        return true;
    }

    public boolean transferFunds(String fromAccount, String toAccount, double amount) {
        double senderBalance = accountDAO.getBalance(fromAccount);

        if (senderBalance < amount || amount <= 0) {
            System.out.println("Insufficient funds for transfer or invalid amount.");
            return false;
        }

        // Deduct from sender
        accountDAO.updateBalance(fromAccount, senderBalance - amount);

        // Add to receiver
        double receiverBalance = accountDAO.getBalance(toAccount);
        accountDAO.updateBalance(toAccount, receiverBalance + amount);

        // Log transactions
        transactionDAO.logTransaction(fromAccount, "TRANSFER_OUT", amount);
        transactionDAO.logTransaction(toAccount, "TRANSFER_IN", amount);

        System.out.println("Transfer successful!");
        return true;
    }
}
