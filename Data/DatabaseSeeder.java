package Data;

import Services.BankService;
import Services.AccountService;
import Services.Transaction;
import Services.SecurityUtils;

public class DatabaseSeeder {
    public static void insertBank(BankService bankService) {
        String pass = SecurityUtils.hashCode("12345678");

        bankService.createBank(2, "BDO", pass);
        bankService.createBank(3, "Metro Bank", pass);
        bankService.createBank(4, "BPI", pass);
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


        String pass = SecurityUtils.hashCode("1111");
        // Bank 1
        accountService.createAccount(2, "10001", 10000.0, creditTypeId, pass, "Alice", "Brown", "alice.brown@example.com");
        accountService.createAccount(2, "10002", 10000.0, creditTypeId, pass, "Bob", "Green", "bob.green@example.com");
        accountService.createAccount(2, "10003", 5000.0, savingsTypeId, pass, "Charlie", "White", "charlie.white@example.com");
        accountService.createAccount(2, "10004", 7500.0, savingsTypeId, pass, "Daisy", "Blue", "daisy.blue@example.com");
        accountService.createAccount(2, "10005", 3000.0, studentTypeId, pass, "Ethan", "Gray", "ethan.gray@example.com");
        accountService.createAccount(2, "10006", 4000.0, studentTypeId, pass, "Fiona", "Black", "fiona.black@example.com");
        accountService.createAccount(2, "10007", 20000.0, businessTypeId, pass, "George", "Red", "george.red@example.com");
        accountService.createAccount(2, "10008", 20000.0, businessTypeId, pass, "Hannah", "Yellow", "hannah.yellow@example.com");

        // Bank 2
        accountService.createAccount(3, "20001", 10000.0, creditTypeId, pass, "Ian", "Gray", "ian.gray@example.com");
        accountService.createAccount(3, "20002", 10000.0, creditTypeId, pass, "Julia", "Stone", "julia.stone@example.com");
        accountService.createAccount(3, "20003", 6000.0, savingsTypeId, pass, "Kevin", "Brown", "kevin.brown@example.com");
        accountService.createAccount(3, "20004", 8500.0, savingsTypeId, pass, "Lara", "White", "lara.white@example.com");
        accountService.createAccount(3, "20005", 2500.0, studentTypeId, pass, "Mark", "Black", "mark.black@example.com");
        accountService.createAccount(3, "20006", 3500.0, studentTypeId, pass, "Nina", "Blue", "nina.blue@example.com");
        accountService.createAccount(3, "20007", 20000.0, businessTypeId, pass, "Oscar", "Red", "oscar.red@example.com");
        accountService.createAccount(3, "20008", 20000.0, businessTypeId, pass, "Paula", "Yellow", "paula.yellow@example.com");

        // Bank 3
        accountService.createAccount(4, "30001", 10000.0, creditTypeId, pass, "Quinn", "Black", "quinn.black@example.com");
        accountService.createAccount(4, "30002", 10000.0, creditTypeId, pass, "Rachel", "Gray", "rachel.gray@example.com");
        accountService.createAccount(4, "30003", 5500.0, savingsTypeId, pass, "Steve", "Brown", "steve.brown@example.com");
        accountService.createAccount(4, "30004", 9000.0, savingsTypeId, pass, "Tina", "White", "tina.white@example.com");
        accountService.createAccount(4, "30005", 3200.0, studentTypeId, pass, "Ulysses", "Green", "ulysses.green@example.com");
        accountService.createAccount(4, "30006", 4100.0, studentTypeId, pass, "Vera", "Blue", "vera.blue@example.com");
        accountService.createAccount(4, "30007", 20000.0, businessTypeId, pass, "Will", "Red", "will.red@example.com");
        accountService.createAccount(4, "30008", 20000.0, businessTypeId, pass, "Xena", "Yellow", "xena.yellow@example.com");
    }

    public static void insertSampleCreditPayments(TransactionDAO transactionDAO) {
        // 1-4 FUNDTRANSFER (Savings to Savings same bank)
        transactionDAO.logTransaction("10001", "10003", Transaction.Transactions.FUNDTRANSFER.toString(), 500, "Transferred 500 from 10001 to 10003");
        transactionDAO.logTransaction("10003", "10001", Transaction.Transactions.RECEIVE_TRANSFER.toString(), 500, "Received 500 from 10001");

        transactionDAO.logTransaction("10004", "10006", Transaction.Transactions.FUNDTRANSFER.toString(), 400, "Transferred 400 from 10004 to 10006");
        transactionDAO.logTransaction("10006", "10004", Transaction.Transactions.RECEIVE_TRANSFER.toString(), 400, "Received 400 from 10004");

        // 5-8 EXTERNAL_TRANSFER (Savings to Savings different bank)
        transactionDAO.logTransaction("10001", "20003", Transaction.Transactions.EXTERNAL_TRANSFER.toString(), 700, "External transfer from 10001 to 20003");
        transactionDAO.logTransaction("20003", "10001", Transaction.Transactions.RECEIVE_TRANSFER.toString(), 700, "Received external transfer from 10001");

        transactionDAO.logTransaction("10004", "20006", Transaction.Transactions.EXTERNAL_TRANSFER.toString(), 600, "External transfer from 10004 to 20006");
        transactionDAO.logTransaction("20006", "10004", Transaction.Transactions.RECEIVE_TRANSFER.toString(), 600, "Received external transfer from 10004");

        // 9-12 PAYMENT (Credit to Savings)
        transactionDAO.logTransaction("10002", "10001", Transaction.Transactions.PAYMENT.toString(), 800, "Credit payment from 10002 to 10001");
        transactionDAO.logTransaction("10001", "10002", Transaction.Transactions.RECEIVE_TRANSFER.toString(), 800, "Received credit payment from 10002");

        transactionDAO.logTransaction("20002", "20003", Transaction.Transactions.PAYMENT.toString(), 900, "Credit payment from 20002 to 20003");
        transactionDAO.logTransaction("20003", "20002", Transaction.Transactions.RECEIVE_TRANSFER.toString(), 900, "Received credit payment from 20002");

        // 13-16 WITHDRAWAL
        transactionDAO.logTransaction("10001", "ATM", Transaction.Transactions.WITHDRAWAL.toString(), 300, "Withdrew 300 from 10001");
        transactionDAO.logTransaction("10003", "ATM", Transaction.Transactions.WITHDRAWAL.toString(), 200, "Withdrew 200 from 10003");

        transactionDAO.logTransaction("20003", "ATM", Transaction.Transactions.WITHDRAWAL.toString(), 500, "Withdrew 500 from 20003");
        transactionDAO.logTransaction("20006", "ATM", Transaction.Transactions.WITHDRAWAL.toString(), 150, "Withdrew 150 from 20006");

        // 17-20 DEPOSIT
        transactionDAO.logTransaction("BANK", "10001", Transaction.Transactions.DEPOSIT.toString(), 1200, "Deposit to 10001");
        transactionDAO.logTransaction("BANK", "10004", Transaction.Transactions.DEPOSIT.toString(), 1400, "Deposit to 10004");

        transactionDAO.logTransaction("BANK", "20003", Transaction.Transactions.DEPOSIT.toString(), 1600, "Deposit to 20003");
        transactionDAO.logTransaction("BANK", "20006", Transaction.Transactions.DEPOSIT.toString(), 1800, "Deposit to 20006");

        // 21-24 COMPENSATION (Manual credit compensation log)
        transactionDAO.logTransaction("10002", "LOAN", Transaction.Transactions.COMPENSATION.toString(), 500, "Manual compensation log for 10002");
        transactionDAO.logTransaction("20002", "LOAN", Transaction.Transactions.COMPENSATION.toString(), 400, "Manual compensation log for 20002");

        transactionDAO.logTransaction("10007", "LOAN", Transaction.Transactions.COMPENSATION.toString(), 300, "Manual compensation log for 10007");
        transactionDAO.logTransaction("20007", "LOAN", Transaction.Transactions.COMPENSATION.toString(), 200, "Manual compensation log for 20007");
    }

    public static void insertSampleData() {
        IDatabaseProvider databaseProvider = new SQLiteDatabaseProvider();
        BankDAO bankDAO = new BankDAO(databaseProvider);
        AccountDAO accountDAO = new AccountDAO(databaseProvider);
        AccountTypeDAO accountTypeDAO = new AccountTypeDAO(databaseProvider);
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
