package Launchers;

import Accounts.*;
import Bank.Bank;
import Main.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;


import Services.SecurityUtils;
import Services.ServiceProvider;
import Services.BankService;
/**
 * BankLauncher handles interactions with the bank module, allowing login,
 * account management, and bank creation.
 */
public class BankLauncher {
    private static final BankService bankService = ServiceProvider.getBankService();
    private final static ArrayList<Bank> banks = new ArrayList<>();
    private static Bank loggedBank;

    public static void loadBanksFromDatabase() {
        banks.clear();  // Clear any old data
        banks.addAll(BankService.fetchAllBanksStatic());  // Add fresh data from DB
        System.out.println("Loaded " + banks.size() + " banks from the database.");
    }
    public static void loadAccountsForAllBanks() {
        for (Bank bank : banks) {
            bank.loadAccountsFromDatabase();
        }
    }

    public static void loadTransactionsForAllAccounts() {
        for (Bank bank : banks) {
            for (Account account : bank.getBankAccounts()) {
                account.loadTransactionsFromDatabase();
            }
        }
        System.out.println("✔ Transactions loaded for all accounts.");
    }

    /**
     * Initializes the banking module, allowing users to log in or create a new bank.
     */
    public static void bankInit() {
        while (isLogged()) {
            Main.showMenuHeader("Banking System");
            Main.showMenu(Menu.BankMenu.menuIdx);
            Main.setOption();

            switch (Main.getOption()) {
                case 1 -> showAccounts();
                case 2 -> newAccounts();
                case 3 -> {
                    logout();
                    System.out.println("Exiting banking system...");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    /**
     * Checks if there is a currently logged-in bank session.
     *
     * @return true if a bank is logged in, false otherwise.
     */
    protected static boolean isLogged() {
        return loggedBank != null;
    }

    /**
     * Adds a new bank to the list of registered banks.
     *
     * @param b The bank to be added.
     */
    public static void addBank(Bank b) {
        if (!banks.contains(b)) {
            banks.add(b);
        } else {
            System.out.println("⚠ Bank already exists.");
        }
    }

    /**
     * Handles the bank login process.
     */
    public static void bankLogin() {
        if (banks.isEmpty()) {
            System.out.println("No banks registered yet. Create a new bank first.");
            return;
        }

        showBanksMenu();

        String bankName = Main.prompt("Enter Bank Name: ", false).trim();

        Bank selectedBank = null;
        for (Bank bank : banks) {
            if (bank.getName().equalsIgnoreCase(bankName)) {
                selectedBank = bank;
                break;
            }
        }

        if (selectedBank == null) {
            System.out.println("❌ Error: No bank found with the name \"" + bankName + "\".");
            return;
        }

        String passcode = Main.prompt("Enter Bank Passcode: ", true).trim();
        String hashedInputPasscode = SecurityUtils.hashCode(passcode.trim());

        if (!selectedBank.getPasscode().equals(hashedInputPasscode)) {
            System.out.println("❌ Error: Incorrect passcode. Access denied.");
            return;
        }

        setLogSession(selectedBank);
        System.out.println("✅ Successfully logged into " + loggedBank.getName());
        bankInit();
    }

    /**
     * Displays a menu of all registered banks.
     */
    public static void showBanksMenu() {
        if (banks.isEmpty()) {
            System.out.println("No banks have been registered yet.");
            return;
        }
        System.out.println("\n📌 List of Registered Banks:");
        System.out.printf("%-3s | %-30s%n", "#", "Bank Name");
        System.out.println("----------------------------------");
        for (int i = 0; i < banks.size(); i++) {
            System.out.printf("%-3d | %-30s%n", i + 1, banks.get(i).getName());
        }
    }

    /**
     * Show the accounts registered to this bank.
     * Must prompt the user to select which type of accounts to show:
     * (1) Credit Accounts, (2) Savings Accounts, (3) All, and (4) Create New Account.
     */
    public static void showAccounts() {
        if (loggedBank == null) {
            System.out.println("No bank logged in.");
            return;
        }

        Main.showMenuHeader("Show Accounts");
        Main.showMenu(Menu.ShowAccounts.menuIdx);
        Main.setOption();

        switch (Main.getOption()) {
            case 1 -> loggedBank.showAccounts(CreditAccount.class);
            case 2 -> loggedBank.showAccounts(SavingsAccount.class);
            case 3 -> loggedBank.showAccounts(StudentAccount.class);
            case 4 -> loggedBank.showAccounts(BusinessAccount.class);
            case 5 -> loggedBank.showAccounts(null);
            default -> System.out.println("Invalid option. Try again.");
        }
    }

    /**
     * Handles the creation of a new account within the currently logged-in bank.
     */
    public static void newAccounts() {
        if (loggedBank == null) {
            System.out.println("No bank is logged in. Please log into a bank first.");
            return;
        }

        while (true) {
            Main.showMenu(Menu.AccountTypeSelection.menuIdx);
            Main.setOption();

            int option = Main.getOption();
            if (option == 5) {
                System.out.println("Returning to Bank Menu...");
                return;
            }

            Account newAccount = switch (option) {
                case 1 -> loggedBank.createNewCreditAccount();
                case 2 -> loggedBank.createNewSavingsAccount();
                case 3 -> loggedBank.createNewStudentAccount();
                case 4 -> loggedBank.createNewBusinessAccount();
                default -> null;
            };

            if (newAccount != null && Bank.accountExists(newAccount.getBank(), newAccount.getAccountNumber())) {
                System.out.println("✅ New account created: " + newAccount);
            } else {
                System.out.println("Account creation failed or canceled.");
            }
        }
    }

    /**
     * Logs into a selected bank session.
     *
     * @param bank The bank to log into.
     */
    private static void setLogSession(Bank bank) {
        loggedBank = bank;
    }

    /**
     * Logs out from the current bank session.
     */
    private static void logout() {
        if (loggedBank != null) {
            System.out.println("Logging out from " + loggedBank.getName());
        }
        loggedBank = null;
    }
    /**
     * Creates a new bank and registers it in the system.
     */
    public static void createNewBank() {

        Field<String, String> bankNameField = new Field<String, String>("Bank Name", String.class, null, new Field.StringFieldValidator());
        bankNameField.setFieldValue("Enter Bank Name: ", false);

        if (bankNameField.getFieldValue().isEmpty()) {
            System.out.println("❌ Error: Bank Name is required!");
            return;
        }

        Field<String, Integer> bankPasscodeField = new Field<String, Integer>("Bank Passcode", String.class, 4, new Field.StringFieldLengthValidator());
        bankPasscodeField.setFieldValue("Enter Bank Passcode: ");

        try {
            Integer.parseInt(bankPasscodeField.getFieldValue());
        } catch (NumberFormatException e) {
            System.out.println("❌ Error: Passcode must be numbers.");
            return;
        }

        if (bankPasscodeField.getFieldValue() == null || bankPasscodeField.getFieldValue().length() < 4 ) {
            System.out.println("❌ Error: Passcode must be at least 4 characters long.");
            return;
        }

        System.out.println("Do you want to set custom deposit, withdrawal, and credit limits? (Y/N): ");
        String choice = Main.prompt("", true).trim().toUpperCase();

        Bank newBank;

        if (choice.equals("Y")) {
            Field<Double, Double> depositLimitField = new Field<Double, Double>("Deposit Limit", Double.class, 0.0, new Field.DoubleFieldValidator());
            depositLimitField.setFieldValue("Enter Deposit Limit: ");

            Field<Double, Double> withdrawLimitField = new Field<Double, Double>("Withdraw Limit", Double.class, 0.0, new Field.DoubleFieldValidator());
            withdrawLimitField.setFieldValue("Enter Withdraw Limit: ");

            Field<Double, Double> creditLimitField = new Field<Double, Double>("Credit Limit", Double.class, 0.0, new Field.DoubleFieldValidator());
            creditLimitField.setFieldValue("Enter Credit Limit: ");

            Field<Double, Double> processingFeeField = new Field<Double, Double>("Processing Fee", Double.class, 0.0, new Field.DoubleFieldValidator());
            processingFeeField.setFieldValue("Enter Processing Fee: ");

            newBank = new Bank(
                    bankSize(),
                    bankNameField.getFieldValue(),
                    bankPasscodeField.getFieldValue(),
                    depositLimitField.getFieldValue(),
                    withdrawLimitField.getFieldValue(),
                    creditLimitField.getFieldValue(),
                    processingFeeField.getFieldValue()
            );

            bankService.createBank(
                    bankSize(),
                    bankNameField.getFieldValue(),
                    bankPasscodeField.getFieldValue(),
                    depositLimitField.getFieldValue(),
                    withdrawLimitField.getFieldValue(),
                    creditLimitField.getFieldValue(),
                    processingFeeField.getFieldValue()
            );

        } else {
            newBank = new Bank(
                    bankSize(),
                    bankNameField.getFieldValue(),
                    bankPasscodeField.getFieldValue()
            );
        }

        Bank sameName = getBank(new Bank.BankComparator(), newBank);
        Bank samePasscode = getBank(new Bank.BankCredentialsComparator(), newBank);
        if (samePasscode == null || sameName == null) {
            addBank(newBank);
            System.out.println("✅ Bank created successfully: " + newBank);
            return;
        }
        System.out.println("❌ Bank already exists...");
    }

        /**
         * Retrieves a bank that matches the given comparator.
         *
         * @param bankComparator Comparator to determine the matching criteria.
         * @param bank Bank object to be compared.
         * @return The matching Bank object, or null if no match is found.
         */
    public static Bank getBank(Comparator<Bank> bankComparator, Bank bank) {
        return banks.stream().filter(b -> bankComparator.compare(b, bank) == 0).findFirst().orElse(null);
    }

    /**
     * Returns the number of registered banks.
     *
     * @return The number of banks.
     */
    public static int bankSize() {
        return banks.size();
    }

    public static ArrayList<Bank> getBanks() {
        return banks;
    }

    /**
     * Finds an account by account number in all registered banks.
     *
     * @param accountNum The account number to search for.
     * @return The account if found, otherwise null.
     */
    public static Account findAccount(String accountNum) {
        return banks.stream()
                .map(bank -> bank.getBankAccount(accountNum))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }
}