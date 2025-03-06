package Bank;

import Accounts.Account;
import Accounts.CreditAccount;
import Accounts.SavingsAccount;
import Main.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class Bank {
    private int ID;
    private String name, passcode;

    private double DEPOSITLIMIT = 50000.0;
    private double WITHDRAWLIMIT = 50000.0;
    private double CREDITLIMIT = 100000.0;
    private double processingFee = 10.00;
    private ArrayList<Account> BANKACCOUNTS = new ArrayList<>();
    private static int accountNumberCounter = 1; // Account Number starts from 1
    private static final Set<String> ACCOUNT_NUMBERS = new HashSet<>(); // Store account numbers

    public Bank(int ID, String name, String passcode) {
        this.ID = ID;
        this.name = name;
        this.passcode = passcode;
    }

    public Bank(int ID, String name, String passcode, double DEPOSITLIMIT, double WITHDRAWLIMIT, double CREDITLIMIT) {
        this(ID, name, passcode);
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
        for (Account account : BANKACCOUNTS){
            if (account.getACCOUNTNUMBER().equals(accountNum)) {
                return account;
            }
        }
        return null;
    }

    public ArrayList<Field<String, ?>> createNewAccount() {
        // TODO Complete this method.
        ArrayList<Field<String, ?>> accountDetails = new ArrayList<>();

        // First Name (Required)
        Field<String, Integer> firstName = new Field<>(
                "First Name", String.class, 2, new Field.StringFieldLengthValidator());
        firstName.setFieldValue("Enter First Name: ");

        // Last Name (Required)
        Field<String, Integer> lastName = new Field<>(
                "Last Name", String.class, 2, new Field.StringFieldLengthValidator());
        lastName.setFieldValue("Enter Last Name: ");

        // Generate Uniqie Account Number
        String generatedAccountNumber = generateAccountNumber();
        System.out.println("Generated Account Number: " + generatedAccountNumber);
        Field<String, String> accountNumber = new Field<>(
                "Account Number", String.class, "", new Field.StringFieldValidator());
        accountNumber.setFieldValue(generatedAccountNumber);

        // Account Holder Email
        Field<String, String> email = new Field<>(
                "Account Holder Email", String.class, "", new Field.StringFieldValidator());
        email.setFieldValue("Enter Email: ");

        // Account Holder Pin (Required)
        Field<String, Integer> pin = new Field<>(
                "Pin", String.class, 4, new Field.StringFieldLengthValidator());
        pin.setFieldValue("Enter 4-digit PIN: ");


        return accountDetails;
    }

    public CreditAccount createNewCreditAccount() {
        // TODO Complete this method.
        ArrayList<Field<String, ?>> accountDetails = createNewAccount();

        // Extracts values from accountDetails
        String firstName = accountDetails.get(0).getFieldValue();
        String lastName = accountDetails.get(1).getFieldValue();
        String accountNumber = accountDetails.get(2).getFieldValue();
        String email = accountDetails.get(3).getFieldValue();
        String pin = accountDetails.get(4).getFieldValue();

        // Create CreditAccount with default 0.0 loan
        CreditAccount creditAccount = new CreditAccount(this, accountNumber, firstName, lastName, email, pin, 0.0);

        // Add to BANKACCOUNTS list
        BANKACCOUNTS.add(creditAccount);

        System.out.println("Credit Account created successfully!");
        return creditAccount;
    }

    public SavingsAccount createNewSavingsAccount() {
        // TODO Complete this method.
        ArrayList<Field<String, ?>> accountDetails = createNewAccount();

        // Extract values from accountDetails
        String firstName = accountDetails.get(0).getFieldValue();
        String lastName = accountDetails.get(1).getFieldValue();
        String accountNumber = accountDetails.get(2).getFieldValue();
        String email = accountDetails.get(3).getFieldValue();
        String pin = accountDetails.get(4).getFieldValue();

        // Create SavingsAccount with default 0.0 balance
        SavingsAccount savingsAccount = new SavingsAccount(this, accountNumber, firstName, lastName, email, pin, 0.0);

        // Add to BANKACCOUNTS list
        BANKACCOUNTS.add(savingsAccount);

        System.out.println("Savings Account created successfully!");
        return savingsAccount;
    }

    public void addNewAccount(Account account) {
        // TODO Complete this method.
        if (account != null) {
            // Initialize BANKACCOUNT list before adding
            if (BANKACCOUNTS == null) {
                BANKACCOUNTS = new ArrayList<>();
            }


            BANKACCOUNTS.add(account);
            System.out.println("Account sucessfully added: " + account.getACCOUNTNUMBER());
        } else {
            System.out.println("Error! Cannot add null account.");
        }
    }

    public static boolean accountExists(Bank bank, String accountNum) {
        // TODO Complete this method.
        if (bank.BANKACCOUNTS != null) {
            for (Account account : bank.BANKACCOUNTS) {
                if (account.getACCOUNTNUMBER().equals(accountNum)) {
                    return true; // Account found
                }
            }
        }
        return false; // Account does not exist
    }



    private static String generateAccountNumber() {
        String accountNumber;
        do {
            // Format number as 5-digit string (00001, 00002, ...)
            accountNumber = String.format("%05d", accountNumberCounter);
            accountNumberCounter++; // Increment for next use

        } while (ACCOUNT_NUMBERS.contains(accountNumber));
        ACCOUNT_NUMBERS.add(accountNumber); // Store generated numbers
        return accountNumber;
    }

    @Override
    public String toString() {
        // TODO Complete this method.
        return super.toString();
    }
}
