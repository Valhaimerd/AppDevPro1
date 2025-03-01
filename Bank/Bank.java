package Bank;

import Accounts.Account;
import Accounts.CreditAccount;
import Accounts.SavingsAccount;
import Main.Field;
import Main.Main;
import com.sun.jdi.Value;

import java.util.ArrayList;
import java.util.Comparator;

public class Bank {
    private int ID;
    private String name, passcode;
    private double DEPOSITLIMIT = 50000.0;
    private double WITHDRAWLIMIT = 50000.0;
    private double CREDITLIMIT = 100000.0;
    private double processingFee = 10.00;
    private ArrayList<Account> BANKACCOUNTS = new ArrayList<>();

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

    public double getProcessingFee() {
        return processingFee;
    }

    public double getDEPOSITLIMIT() {
        return DEPOSITLIMIT;
    }

    public double getCREDITLIMIT() {
        return CREDITLIMIT;
    }

    public double getWITHDRAWLIMIT() {
        return WITHDRAWLIMIT;
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

    public void sortAccounts() {BANKACCOUNTS.sort(Comparator.comparing(Account::getACCOUNTNUMBER));}

    public <T extends  Account> void showAccounts(Class<T> accountType) {
        // TODO Complete this method.
        BANKACCOUNTS.parallelStream()
                .filter(accountType::isInstance)
                .forEach(System.out::println);
    }

    public Account getBankAccount(Bank bank, String accountNum) {
        // TODO Complete this method. (Efficiency)
        for (Account account : BANKACCOUNTS) {
            return account.getBank().equals(bank) && account.getACCOUNTNUMBER().equals(accountNum) ? account : null;
        }
        return null;
    }

    public ArrayList<Field<String, ?>> createNewAccount() {
        ArrayList<Field<String, ?>> accountInfo = new ArrayList<>();

        Field<String, String> accountNumber = new Field<String, String>("Account Number", String.class, null, new Field.StringFieldValidator());
        Field<String, String> firstName = new Field<String, String>("First Name", String.class, null, new Field.StringFieldValidator());
        Field<String, String> lastName = new Field<String, String>("Last Name", String.class, null, new Field.StringFieldValidator());
        Field<String, String> email = new Field<String, String>("Email", String.class, null, new Field.StringFieldValidator());
        Field<String, Integer> pin = new Field<String, Integer>("Pin", String.class, 4, new Field.StringFieldLengthValidator());

        accountInfo.add(accountNumber);
        accountInfo.add(firstName);
        accountInfo.add(lastName);
        accountInfo.add(email);
        accountInfo.add(pin);

        firstName.setFieldValue("Enter First Name: ", false);
        lastName.setFieldValue("Enter Last Name: ", false);
        email.setFieldValue("Enter Email: ", false);
        accountNumber.setFieldValue("Enter Account Number: ", true);
        pin.setFieldValue("Enter 4-digit PIN: ", true);

        return accountInfo;
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
