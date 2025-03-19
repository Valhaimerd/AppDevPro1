package Services;
import Bank.Bank;
import Data.IBankDAO;
import java.util.List;

public class BankService {
    private final IBankDAO bankDAO;

    public List<Bank> fetchAllBanks() {
        return bankDAO.getAllBanksFull();
    }

    public static List<Bank> fetchAllBanksStatic() {
        BankService bankService = ServiceProvider.getBankService();
        return bankService.fetchAllBanks();
    }

    public BankService(IBankDAO bankDAO) {
        this.bankDAO = bankDAO;
    }

    public void createBank(int bankId, String name, String passcode) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Bank name cannot be empty.");
            return;
        }
        if (passcode == null || passcode.trim().isEmpty()) {
            System.out.println("Passcode cannot be empty.");
            return;
        }

        bankDAO.addBank(bankId, name, passcode);
        System.out.println("Bank added successfully!");
    }

    public void createBank(int bankId, String name, String passcode,
                           double depositLimit, double withdrawLimit,
                           double creditLimit, double processingFee) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Bank name cannot be empty.");
            return;
        }
        if (passcode == null || passcode.trim().isEmpty()) {
            System.out.println("Passcode cannot be empty.");
            return;
        }

        bankDAO.addBank(bankId, name, passcode, depositLimit, withdrawLimit, creditLimit, processingFee);
        System.out.println("Bank added successfully with custom settings!");
    }

}
