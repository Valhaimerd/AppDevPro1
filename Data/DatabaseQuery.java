package Data;

import java.util.List;

public class DatabaseQuery {
    private final IBankDAO bankDAO;

    public DatabaseQuery(IBankDAO bankDAO) {
        this.bankDAO = bankDAO;
    }

    public void fetchAccounts() {
        List<String> banks = bankDAO.getAllBanks();
        for (String bank : banks) {
            System.out.println("Bank: " + bank);
        }
    }

    public static void main(String[] args) {
        IDatabaseProvider databaseProvider = new SQLiteDatabaseProvider();
        DatabaseQuery query = new DatabaseQuery(new BankDAO(databaseProvider));
        query.fetchAccounts();
    }
}
