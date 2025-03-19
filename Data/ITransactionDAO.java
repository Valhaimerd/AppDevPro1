package Data;

import Services.Transaction;

import java.util.List;

public interface ITransactionDAO {
    void logTransaction(String sourceAccount, String targetAccount, String type, double amount, String description);
    List<Transaction> getTransactionsForAccount(String accountNumber);
}

