package Data;

import Services.BankService;
import Services.AccountService;
import Services.Transaction;

public class DatabaseSeeder {
    public static void insertBank(BankService bankService) {
        bankService.createBank(1, "DDO", "12345678");
    }

    public static void insertAccountTypes(AccountTypeDAO accountTypeDAO) {
        accountTypeDAO.insertAccountType("Credit");
        accountTypeDAO.insertAccountType("Savings");
        accountTypeDAO.insertAccountType("Business");
        accountTypeDAO.insertAccountType("Student");
    }

    public static void insertAccounts(AccountService accountService, AccountTypeDAO accountTypeDAO) {
        int savingsTypeId = accountTypeDAO.getAccountTypeId("Savings");
        int creditTypeId = accountTypeDAO.getAccountTypeId("Credit");

        accountService.createAccount(
                1,                       // bankId
                "ACC001",                // accountNumber
                5000.00,                 // balance
                savingsTypeId,           // accountTypeId
                "1234",                  // pin
                "John",                  // ownerFname
                "Doe",                   // ownerLname
                "john.doe@example.com"   // ownerEmail
        );
        accountService.createAccount(
                1,                       // bankId
                "ACC002",                // accountNumber
                10000.00,                // balance
                creditTypeId,            // accountTypeId
                "5678",                  // pin
                "Jane",                  // ownerFname
                "Smith",                 // ownerLname
                "jane.smith@example.com" // ownerEmail
        );
    }

    public static void insertSampleCreditPayments(TransactionDAO transactionDAO) {
        // Example: ACC002 (credit account) pays 1000.00 to ACC001 (savings)
        double paymentAmount = 1000.00;
        String creditAccount = "ACC002";
        String savingsAccount = "ACC001";

        // Log for credit account
        transactionDAO.logTransaction(
                creditAccount,
                savingsAccount,
                Transaction.Transactions.PAYMENT.toString(), // use enum value
                paymentAmount,
                "Paid to account " + savingsAccount
        );

        // Log for savings account
        transactionDAO.logTransaction(
                savingsAccount,
                creditAccount,
                Transaction.Transactions.RECEIVE_TRANSFER.toString(), // use enum value
                paymentAmount,
                "Received credit payment from " + creditAccount
        );

    }
    public static void insertSampleTransfers(TransactionDAO transactionDAO) {
        // Example: ACC001 transfers 2000.00 to ACC002
        double transferAmount = 2000.00;
        String senderAccount = "ACC001";
        String receiverAccount = "ACC002";

        // Log for sender
        transactionDAO.logTransaction(
                senderAccount,
                receiverAccount,
                "TRANSFER_OUT",
                transferAmount,
                "Transferred to account " + receiverAccount
        );

        // Log for receiver
        transactionDAO.logTransaction(
                receiverAccount,
                senderAccount,
                "TRANSFER_IN",
                transferAmount,
                "Received from account " + senderAccount
        );
    }

    public static void insertSampleData() {
        IDatabaseProvider databaseProvider = new SQLiteDatabaseProvider();
        BankDAO bankDAO = new BankDAO(databaseProvider);
        AccountDAO accountDAO = new AccountDAO(databaseProvider);
        AccountTypeDAO accountTypeDAO = new AccountTypeDAO();
        TransactionDAO transactionDAO = new TransactionDAO(databaseProvider);
        BankService bankService = new BankService(bankDAO);
        AccountService accountService = new AccountService(accountDAO);

//        insertAccountTypes(accountTypeDAO);

        insertBank(bankService);
        insertAccounts(accountService, accountTypeDAO);
        insertSampleCreditPayments(transactionDAO);

        System.out.println("All sample data inserted successfully.");
    }

    public static void main(String[] args) {
        insertSampleData();
    }
}
