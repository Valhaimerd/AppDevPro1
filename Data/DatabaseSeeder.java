package Data;

import Services.BankService;

public class DatabaseSeeder {
    public static void insertSampleData() {
        IDatabaseProvider databaseProvider = new SQLiteDatabaseProvider();

        BankDAO bankDAO = new BankDAO(databaseProvider);
        AccountDAO accountDAO = new AccountDAO(databaseProvider);
        AccountTypeDAO accountTypeDAO = new AccountTypeDAO();

        // Use service instead of direct DAO
        BankService bankService = new BankService(bankDAO);
        bankService.createBank(1, "DDO", "12345678");

        System.out.println("Sample data inserted successfully.");
    }

    public static void main(String[] args) {
        insertSampleData();
    }
}
