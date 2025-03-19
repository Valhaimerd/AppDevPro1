package Data;

import Services.BankService;
import Services.AccountService;
import Services.Transaction;

public class DatabaseSeeder {
    public static void insertBank(BankService bankService) {
        bankService.createBank(1, "BDO", "12345678");
        bankService.createBank(2, "Metro Bank", "12345678");
        bankService.createBank(3, "BPD", "12345678");
    }

    public static void insertAccountTypes(AccountTypeDAO accountTypeDAO) {
        accountTypeDAO.insertAccountType("Credit");
        accountTypeDAO.insertAccountType("Savings");
        accountTypeDAO.insertAccountType("Student");
        accountTypeDAO.insertAccountType("Business");
    }

    public static void insertAccounts(AccountService accountService, AccountTypeDAO accountTypeDAO) {
        int savingsTypeId = accountTypeDAO.getAccountTypeId("Savings");
        int creditTypeId = accountTypeDAO.getAccountTypeId("Credit");
        int studentTypeId = accountTypeDAO.getAccountTypeId("Student");
        int businessTypeId = accountTypeDAO.getAccountTypeId("Business");

        // Bank 1
        accountService.createAccount(1, "10001", 10000.0, creditTypeId, "1111", "Alice", "Brown", "alice.brown@example.com");
        accountService.createAccount(1, "10002", 10000.0, creditTypeId, "2222", "Bob", "Green", "bob.green@example.com");
        accountService.createAccount(1, "10003", 5000.0, savingsTypeId, "3333", "Charlie", "White", "charlie.white@example.com");
        accountService.createAccount(1, "10004", 7500.0, savingsTypeId, "4444", "Daisy", "Blue", "daisy.blue@example.com");
        accountService.createAccount(1, "10005", 3000.0, studentTypeId, "5555", "Ethan", "Gray", "ethan.gray@example.com");
        accountService.createAccount(1, "10006", 4000.0, studentTypeId, "6666", "Fiona", "Black", "fiona.black@example.com");
        accountService.createAccount(1, "10007", 20000.0, businessTypeId, "7777", "George", "Red", "george.red@example.com");
        accountService.createAccount(1, "10008", 20000.0, businessTypeId, "8888", "Hannah", "Yellow", "hannah.yellow@example.com");

        // Bank 2
        accountService.createAccount(2, "20001", 10000.0, creditTypeId, "9111", "Ian", "Gray", "ian.gray@example.com");
        accountService.createAccount(2, "20002", 10000.0, creditTypeId, "9222", "Julia", "Stone", "julia.stone@example.com");
        accountService.createAccount(2, "20003", 6000.0, savingsTypeId, "9333", "Kevin", "Brown", "kevin.brown@example.com");
        accountService.createAccount(2, "20004", 8500.0, savingsTypeId, "9444", "Lara", "White", "lara.white@example.com");
        accountService.createAccount(2, "20005", 2500.0, studentTypeId, "9555", "Mark", "Black", "mark.black@example.com");
        accountService.createAccount(2, "20006", 3500.0, studentTypeId, "9666", "Nina", "Blue", "nina.blue@example.com");
        accountService.createAccount(2, "20007", 20000.0, businessTypeId, "9777", "Oscar", "Red", "oscar.red@example.com");
        accountService.createAccount(2, "20008", 20000.0, businessTypeId, "9888", "Paula", "Yellow", "paula.yellow@example.com");

        // Bank 3
        accountService.createAccount(3, "30001", 10000.0, creditTypeId, "3111", "Quinn", "Black", "quinn.black@example.com");
        accountService.createAccount(3, "30002", 10000.0, creditTypeId, "3222", "Rachel", "Gray", "rachel.gray@example.com");
        accountService.createAccount(3, "30003", 5500.0, savingsTypeId, "3333", "Steve", "Brown", "steve.brown@example.com");
        accountService.createAccount(3, "30004", 9000.0, savingsTypeId, "3444", "Tina", "White", "tina.white@example.com");
        accountService.createAccount(3, "30005", 3200.0, studentTypeId, "3555", "Ulysses", "Green", "ulysses.green@example.com");
        accountService.createAccount(3, "30006", 4100.0, studentTypeId, "3666", "Vera", "Blue", "vera.blue@example.com");
        accountService.createAccount(3, "30007", 20000.0, businessTypeId, "3777", "Will", "Red", "will.red@example.com");
        accountService.createAccount(3, "30008", 20000.0, businessTypeId, "3888", "Xena", "Yellow", "xena.yellow@example.com");
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

    public static void insertSampleData() {
        IDatabaseProvider databaseProvider = new SQLiteDatabaseProvider();
        BankDAO bankDAO = new BankDAO(databaseProvider);
        AccountDAO accountDAO = new AccountDAO(databaseProvider);
        AccountTypeDAO accountTypeDAO = new AccountTypeDAO();
        TransactionDAO transactionDAO = new TransactionDAO(databaseProvider);
        BankService bankService = new BankService(bankDAO);
        AccountService accountService = new AccountService(accountDAO);

        insertAccountTypes(accountTypeDAO);

        insertBank(bankService);
        insertAccounts(accountService, accountTypeDAO);
//        insertSampleCreditPayments(transactionDAO);

        System.out.println("All sample data inserted successfully.");
    }

    public static void main(String[] args) {
        insertSampleData();
    }
}
