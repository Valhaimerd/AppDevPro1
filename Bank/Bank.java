package Bank;

import Accounts.*;
import Main.Field;
import Main.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

import Services.AccountService;
import Services.ServiceProvider;

/**
 * The Bank class represents a banking institution that manages multiple accounts.
 * It enforces banking rules such as deposit/withdrawal limits and credit limits.
 */
public class Bank {
    private final String bankName, passcode;
    private final int bankId;
    private final ArrayList<Account> bankAccounts;

    private final double depositLimit, withdrawLimit, creditLimit;
    private final double processingFee;

    private final AccountService accountService = ServiceProvider.getAccountService();

    /**
     * Loads accounts from the database and populates the bankAccounts list.
     */
    public void loadAccountsFromDatabase() {
        bankAccounts.clear();
        List<Account> allAccounts = ServiceProvider.getAccountService().fetchAllAccounts();
        for (Account acc : allAccounts) {
            if (acc.getBank().getBankId() == this.bankId) {
                bankAccounts.add(acc);
            }
        }
        System.out.println("Loaded " + bankAccounts.size() + " accounts for bank " + getName() + " from the database.");
    }

    /**
     * Default constructor with predefined banking limits.
     * @param bankName Name of the bank.
     * @param bankId Unique identifier for the bank.
     * @param passcode Security passcode for the bank.
     */
    public Bank(int bankId, String bankName, String passcode) {
        this.bankId = bankId;
        this.bankName = bankName;
        this.passcode = passcode;
        this.bankAccounts = new ArrayList<>();

        // Default banking limits
        this.depositLimit = 50000.0;
        this.withdrawLimit = 50000.0;
        this.creditLimit = 100000.0;
        this.processingFee = 10.0;
    }

    /**
     * Custom constructor allowing custom banking limits.
     * @param bankId Bank ID.
     * @param bankName Bank name.
     * @param passcode Bank passcode.
     * @param depositLimit Deposit limit for the bank.
     * @param withdrawLimit Withdrawal limit for the bank.
     * @param creditLimit Credit limit for the bank.
     * @param processingFee Processing fee for external transactions.
     */
    public Bank(int bankId, String bankName, String passcode, double depositLimit, double withdrawLimit, double creditLimit, double processingFee) {
        this.bankId = bankId;
        this.bankName = bankName;
        this.passcode = passcode;
        this.bankAccounts = new ArrayList<>();

        this.depositLimit = depositLimit;
        this.withdrawLimit = withdrawLimit;
        this.creditLimit = creditLimit;
        this.processingFee = processingFee;
    }

    /**
     * Creates a new account with validated input fields.
     *
     * @return A list of Field objects containing account details.
     */
    public ArrayList<Field<?, ?>> createNewAccount() {
        ArrayList<Field<?, ?>> accountFields = new ArrayList<>();

        // Create fields with appropriate validation
        Field<String, Integer> accountNumberField = new Field<String, Integer>("Account Number", String.class, 5, new Field.StringFieldLengthValidator());

        Field<String, Integer> pinField = new Field<String, Integer>("PIN", String.class, 4, new Field.StringFieldLengthValidator());

        Field<String, String> firstNameField = new Field<String, String>("First Name", String.class, null, new Field.StringFieldValidator());

        Field<String, String> lastNameField = new Field<String, String>("Last Name", String.class, null, new Field.StringFieldValidator());

        Field<String, String> emailField = new Field<String, String>("Email", String.class, null, new Field.StringFieldValidator());

        Field<?, ?>[] fields = {accountNumberField, pinField, firstNameField, lastNameField, emailField};

        for (Field<?, ?> field : fields) {
            field.setFieldValue("Enter " + field.getFieldName() + ": ");
            accountFields.add(field);
        }

        return accountFields;
    }

        /**
         * Creates and registers a new SavingsAccount using validated fields.
         *
         * @return The newly created SavingsAccount.
         */
    public SavingsAccount createNewSavingsAccount() {
        Main.showMenuHeader("Create New Savings Account");
        ArrayList<Field<?, ?>> accountData = createNewAccount();
        String accountNumber = (String) accountData.get(0).getFieldValue();
        String pin = (String) accountData.get(1).getFieldValue();
        String firstName = (String) accountData.get(2).getFieldValue();
        String lastName = (String) accountData.get(3).getFieldValue();
        String email = (String) accountData.get(4).getFieldValue();

        // Use Main.prompt() instead of new Scanner(System.in)
        double initialDeposit = Double.parseDouble(Main.prompt("Enter Initial Deposit: ", true));

        SavingsAccount newAccount = new SavingsAccount(this, accountNumber, pin, firstName, lastName, email, initialDeposit);
        boolean verify = addNewAccount(newAccount);
        if (verify) {
            ServiceProvider.getAccountService().createAccount(
                    this.bankId, accountNumber, initialDeposit, 2, pin, firstName, lastName, email
            );
        }
        return newAccount;
    }

    /**
     * Creates and registers a new CreditAccount using validated fields.
     *
     * @return The newly created CreditAccount.
     */
    public CreditAccount createNewCreditAccount() {
        Main.showMenuHeader("Create New Credit Account");
        ArrayList<Field<?, ?>> accountData = createNewAccount();
        String accountNumber = (String) accountData.get(0).getFieldValue();
        String pin = (String) accountData.get(1).getFieldValue();
        String firstName = (String) accountData.get(2).getFieldValue();
        String lastName = (String) accountData.get(3).getFieldValue();
        String email = (String) accountData.get(4).getFieldValue();

        CreditAccount newAccount = new CreditAccount(this, accountNumber, pin, firstName, lastName, email);
        boolean verify = addNewAccount(newAccount);
        if (verify) {
            ServiceProvider.getAccountService().createAccount(
                    this.bankId, accountNumber, 0.0, 2, pin, firstName, lastName, email
            );
        }
        return newAccount;
    }

    /**
     * Creates and registers a new StudentAccount using validated fields.
     *
     * @return The newly created StudentAcocunt.
     */
    public StudentAccount createNewStudentAccount() {
        Main.showMenuHeader("Create New Students Account");
        ArrayList<Field<?, ?>> accountData = createNewAccount();
        String accountNumber = (String) accountData.get(0).getFieldValue();
        String pin = (String) accountData.get(1).getFieldValue();
        String firstName = (String) accountData.get(2).getFieldValue();
        String lastName = (String) accountData.get(3).getFieldValue();
        String email = (String) accountData.get(4).getFieldValue();

        double initialDeposit = Double.parseDouble(Main.prompt("Enter Initial Deposit: ", true));

        StudentAccount newAccount = new StudentAccount(this, accountNumber, firstName, lastName, email, pin, initialDeposit);
        boolean verify = addNewAccount(newAccount);
        if (verify) {
            ServiceProvider.getAccountService().createAccount(
                    this.bankId, accountNumber, initialDeposit, 2, pin, firstName, lastName, email
            );
        }
        return newAccount;
    }

    /**
     * Creates and registers a new BusinessAccount using validated fields.
     *
     * @return The newly created BusinessAccount.
     */
    public BusinessAccount createNewBusinessAccount() {
        Main.showMenuHeader("Create New Business Account");
        ArrayList<Field<?, ?>> accountData = createNewAccount();
        String accountNumber = (String) accountData.get(0).getFieldValue();
        String pin = (String) accountData.get(1).getFieldValue();
        String firstName = (String) accountData.get(2).getFieldValue();
        String lastName = (String) accountData.get(3).getFieldValue();
        String email = (String) accountData.get(4).getFieldValue();

        double initialLoan = Double.parseDouble(Main.prompt("Enter Initial Loan Amount: ", true));

        BusinessAccount newAccount = new BusinessAccount(this, accountNumber, firstName, lastName, email, pin, initialLoan);
        boolean verify = addNewAccount(newAccount);
        if (verify) {
            ServiceProvider.getAccountService().createAccount(
                    this.bankId, accountNumber, initialLoan, 2, pin, firstName, lastName, email
            );
        }
        return newAccount;
    }

    /**
     * Validate if the inputted AccountNumber and PIN has any letters. >>Helper Method when adding an Account
     *
     * @param accountNumber An account number of a newly created account.
     * @param pin A pin of a newly created account.
     * @return True if the string is convertable to integers else false.
     */
    public boolean areNumber(String accountNumber, String pin) {
        try {
            Integer.parseInt(accountNumber);
            Integer.parseInt(pin);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("❌ Error: Account Number and PIN must be numbers.");
            return false;
        }
    }

    /**
     * Adds a new account to the bank if the account number is unique.
     *
     * @param account The account to add.
     */
    public boolean addNewAccount(Account account) {
        if (accountExists(this, account.getAccountNumber())) { // Only check within the same bank
            System.out.println("Account number already exists in this bank! Registration failed.");
            return false;
        } else if (!areNumber(account.getAccountNumber(), account.getPin())) return false;

        bankAccounts.add(account);
        return true;
    }

    /**
     * Checks if an account exists in the given bank based on an account number.
     * This method is static because it does not require an instance of the Bank class.
     *
     * @param bank        The bank to search for the account.
     * @param accountNum  The account number to check.
     * @return true if the account exists, false otherwise.
     */
    public static boolean accountExists(Bank bank, String accountNum) {
        if (bank == null || bank.getBankAccounts() == null) {
            return false; // Handle null cases safely
        }

        return bank.getBankAccounts().stream()
                .anyMatch(account -> account.getAccountNumber().equals(accountNum));
    }

    /**
     * Displays all accounts registered under this bank.
     * <p>
     * If no accounts are registered, it will notify the user.
     * The method can show either:
     *  - All accounts (if accountType is null)
     *  - Accounts of a specific type (SavingsAccount or CreditAccount), if specified.
     *
     * @param accountType The specific account type class to filter and display (e.g., SavingsAccount.class).
     *                    If null, displays all account types.
     * @param <T>         A generic type parameter that extends Account, used for type filtering.
     */
    public <T extends Account> void showAccounts(Class<T> accountType) {
        if (bankAccounts.isEmpty()) {
            System.out.println("No account(s) exist..");
            return;
        }

        if (accountType == null) {
            for (Account account : bankAccounts) {
                System.out.println(account);
            }
        } else {
            System.out.println("Showing accounts of type: " + accountType.getSimpleName());

            for (Account account : bankAccounts) {
                if (account.getClass().equals(accountType)) { // Fix: Use exact class matching
                    System.out.println(account);
                }
            }
        }
    }

    public String getName() {
        return bankName;
    }

    public int getBankId() {
        return this.bankId;
    }

    public String getPasscode() {
        return passcode;
    }

    public ArrayList<Account> getBankAccounts() {
        return new ArrayList<>(bankAccounts);
    }

    public double getDepositLimit() {
        return depositLimit;
    }

    public double getWithdrawLimit() {
        return this.withdrawLimit;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public double getProcessingFee() {
        return processingFee;
    }

    /**
     * Retrieves an account from this bank by its account number.
     * @param accountNum Account number to search for.
     * @return Account if found, otherwise null.
     */
    public Account getBankAccount(String accountNum) {

//        for (Account account : bankAccounts) {
//            if (account.getAccountNumber().equals(accountNum)) {
//                return account;
//            }
//        }
//        return null;
        Account account = accountService.fetchAccountByNumber(accountNum);
        if (account == null) {
            System.out.println("⚠️ No account found for account number: " + accountNum);
        }
        return account;
    }

    /**
     * Provides a string representation of the Bank details.
     *
     * @return Formatted Bank details.
     */
    @Override
    public String toString() {
        return String.format(
                "Bank ID: '%s', Bank Name: '%s', Passcode: '%s', Deposit Limit: $%.2f, Withdraw Limit: $%.2f, Credit Limit: $%.2f, Processing Fee: $%.2f, Accounts Registered: %d",
                bankId, bankName, passcode, depositLimit, withdrawLimit, creditLimit, processingFee, bankAccounts.size()
        );
    }

    /**
     * Comparator class used to compare two Bank objects based on their name and passcode.
     * Primarily used for verifying bank credentials during login or searches.
     */
    public static class BankCredentialsComparator implements Comparator<Bank> {
        @Override
        public int compare(Bank b1, Bank b2) {
            int nameComparison = b1.getName().compareTo(b2.getName());
            if (nameComparison != 0) {
                return nameComparison;
            }
            return b1.getPasscode().compareTo(b2.getPasscode());
        }
    }

    /**
     * Comparator class that compares two Bank objects based on their bank ID.
     * Useful for identifying banks by their unique ID.
     */
    public static class BankIdComparator implements Comparator<Bank> {
        @Override
        public int compare(Bank b1, Bank b2) {
            return Integer.compare(b1.getBankId(), b2.getBankId());
        }
    }

    /**
     * Comparator class used to compare two Bank objects by their bank name.
     * Mainly used for sorting or searching banks alphabetically.
     */
    public static class BankComparator implements Comparator<Bank> {
        @Override
        public int compare(Bank b1, Bank b2) {
            return b1.getName().compareTo(b2.getName());
        }
    }
}