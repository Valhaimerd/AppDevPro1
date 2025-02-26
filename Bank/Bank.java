package Bank;

import Accounts.Account;
import Accounts.CreditAccount;
import Accounts.SavingsAccount;
import Main.Field;

import java.util.ArrayList;

public class Bank {
    private int ID;
    private String name, passcode;
    private double DEPOSITLIMIT = 50000.0;
    private double WITHDRAWLIMIT = 50000.0;
    private double CREDITLIMIT = 100000.0;
    private double processingFee = 10.00;
    private ArrayList<Account> BANKACCOUNTS;

    public Bank(int ID, String name, String passcode) {
        this.ID = ID;
        this.name = name;
        this.passcode = passcode;
    }

    public Bank(int ID, String name, String passcode, double DEPOSITLIMIT, double WITHDRAWLIMIT, double CREDITLIMIT) {
        this.ID = ID;
        this.name = name;
        this.passcode = passcode;
        this.DEPOSITLIMIT = DEPOSITLIMIT;
        this.WITHDRAWLIMIT = WITHDRAWLIMIT;
        this.CREDITLIMIT = CREDITLIMIT;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    public <T extends  Account> void showAccounts(Class<T> accountType) {
        // TODO Complete this method.
    }

    public Account getBankAccount(Bank bank, String accountNum) {
        // TODO Complete this method. (Efficiency)
        for (Account account : BANKACCOUNTS) {
            return account.getBank().equals(bank) && account.getACCOUNTNUMBER().equals(accountNum) ? account : null;
        }
        return null;
    }

    public ArrayList<Field<String, ?>> createNewAccount() {
        // TODO Complete this method.
        return null;
    }

    public CreditAccount createNewCreditAccount() {
        // TODO Complete this method.
        return null;
    }

    public SavingsAccount createNewSavingsAccount() {
        // TODO Complete this method.
        return null;
    }

    public void addNewAccount(Account account) {
        // TODO Complete this method.
    }

    public static boolean accountExists(Bank bank, String accountNum) {
        // TODO Complete this method.
        return false;
    }

    @Override
    public String toString() {
        // TODO Complete this method.
        return super.toString();
    }
}
