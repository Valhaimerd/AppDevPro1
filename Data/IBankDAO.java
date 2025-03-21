package Data;

import Bank.Bank;

import java.util.List;

public interface IBankDAO {
    void addBank(int bankId, String name, String passcode);

    /**
     * Adds a bank with full customization, including bank ID, limits, and fees.
     */
    void addBank(int bankId, String name, String passcode,
                 double depositLimit, double withdrawLimit,
                 double creditLimit, double processingFee);

    List<Bank> getAllBanksFull();
    Bank getDBBankByID(int id);
    Bank getDBBankByName(String name);
}