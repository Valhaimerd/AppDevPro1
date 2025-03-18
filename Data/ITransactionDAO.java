package Data;

public interface ITransactionDAO {
    void logTransaction(String accountId, String type, double amount);
}