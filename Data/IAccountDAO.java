package Data;

import java.util.List;

public interface IAccountDAO {
    void addAccount(int bankId, String accountNumber, double balance, int accountTypeId);

    double getBalance(String accountNumber);
    void updateBalance(String accountNumber, double newBalance);
}
