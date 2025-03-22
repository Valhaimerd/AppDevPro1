package Data;

import Services.BankService;
import Services.AccountService;
import Services.Transaction;

public class DatabaseSeeder {
    public static void insertBank(BankService bankService) {
        bankService.createBank(1, "BDO", "12345678");
        bankService.createBank(2, "Metro Bank", "12345678");
        bankService.createBank(3, "BPI", "12345678");
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
        accountService.createAccount(1, "10002", 10000.0, creditTypeId, "1111", "Bob", "Green", "bob.green@example.com");
        accountService.createAccount(1, "10003", 5000.0, savingsTypeId, "1111", "Charlie", "White", "charlie.white@example.com");
        accountService.createAccount(1, "10004", 7500.0, savingsTypeId, "1111", "Daisy", "Blue", "daisy.blue@example.com");
        accountService.createAccount(1, "10005", 3000.0, studentTypeId, "1111", "Ethan", "Gray", "ethan.gray@example.com");
        accountService.createAccount(1, "10006", 4000.0, studentTypeId, "1111", "Fiona", "Black", "fiona.black@example.com");
        accountService.createAccount(1, "10007", 20000.0, businessTypeId, "1111", "George", "Red", "george.red@example.com");
        accountService.createAccount(1, "10008", 20000.0, businessTypeId, "1111", "Hannah", "Yellow", "hannah.yellow@example.com");

        // Bank 2
        accountService.createAccount(2, "20001", 10000.0, creditTypeId, "1111", "Ian", "Gray", "ian.gray@example.com");
        accountService.createAccount(2, "20002", 10000.0, creditTypeId, "1111", "Julia", "Stone", "julia.stone@example.com");
        accountService.createAccount(2, "20003", 6000.0, savingsTypeId, "1111", "Kevin", "Brown", "kevin.brown@example.com");
        accountService.createAccount(2, "20004", 8500.0, savingsTypeId, "1111", "Lara", "White", "lara.white@example.com");
        accountService.createAccount(2, "20005", 2500.0, studentTypeId, "1111", "Mark", "Black", "mark.black@example.com");
        accountService.createAccount(2, "20006", 3500.0, studentTypeId, "1111", "Nina", "Blue", "nina.blue@example.com");
        accountService.createAccount(2, "20007", 20000.0, businessTypeId, "1111", "Oscar", "Red", "oscar.red@example.com");
        accountService.createAccount(2, "20008", 20000.0, businessTypeId, "1111", "Paula", "Yellow", "paula.yellow@example.com");

        // Bank 3
        accountService.createAccount(3, "30001", 10000.0, creditTypeId, "1111", "Quinn", "Black", "quinn.black@example.com");
        accountService.createAccount(3, "30002", 10000.0, creditTypeId, "1111", "Rachel", "Gray", "rachel.gray@example.com");
        accountService.createAccount(3, "30003", 5500.0, savingsTypeId, "1111", "Steve", "Brown", "steve.brown@example.com");
        accountService.createAccount(3, "30004", 9000.0, savingsTypeId, "1111", "Tina", "White", "tina.white@example.com");
        accountService.createAccount(3, "30005", 3200.0, studentTypeId, "1111", "Ulysses", "Green", "ulysses.green@example.com");
        accountService.createAccount(3, "30006", 4100.0, studentTypeId, "1111", "Vera", "Blue", "vera.blue@example.com");
        accountService.createAccount(3, "30007", 20000.0, businessTypeId, "1111", "Will", "Red", "will.red@example.com");
        accountService.createAccount(3, "30008", 20000.0, businessTypeId, "1111", "Xena", "Yellow", "xena.yellow@example.com");
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

        insertAccountTypes(accountTypeDAO);
        insertBank(bankService);
        insertAccounts(accountService, accountTypeDAO);
        insertSampleCreditPayments(transactionDAO);

        System.out.println("All sample data inserted successfully.");
    }

    public static void main(String[] args) {
        insertSampleData();
    }
}
