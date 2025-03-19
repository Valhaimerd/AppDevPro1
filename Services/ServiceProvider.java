package Services;

import Data.*;

public class ServiceProvider {
    private static final SQLiteDatabaseProvider databaseProvider = new SQLiteDatabaseProvider();

    private static final BankService bankService = new BankService(new BankDAO(databaseProvider));
    private static final AccountService accountService = new AccountService(new AccountDAO(databaseProvider));
    private static final LogService transactionService = new LogService(new TransactionDAO(databaseProvider));

    public static BankService getBankService() {
        return bankService;
    }

    public static AccountService getAccountService() {
        return accountService;
    }

    public static LogService getTransactionService() {
        return transactionService;
    }
}
