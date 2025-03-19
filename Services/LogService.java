package Services;

import Data.ITransactionDAO;

import java.util.List;

public class LogService {
    private final ITransactionDAO transactionDAO;

    public LogService(ITransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    public List<Transaction> fetchTransactionsForAccount(String accountNumber) {
        return transactionDAO.getTransactionsForAccount(accountNumber);
    }

    public void logTransaction(String sourceAccount, String targetAccount, String type, double amount, String description) {
        transactionDAO.logTransaction(sourceAccount, targetAccount, type, amount, description);
        System.out.println("Transaction logged: " + description);
    }
}
