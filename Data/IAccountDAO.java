package Data;

import Accounts.Account;

import java.util.List;

public interface IAccountDAO {
    void addAccount(
            int bankId,
            String accountNumber,
            double balance,
            int accountTypeId,
            String pin,
            String ownerFname,
            String ownerLname,
            String ownerEmail
    );

    double getBalance(String accountNumber);
    void updateBalance(String accountNumber, double newBalance);
    List<Account> getAllAccounts();
}
