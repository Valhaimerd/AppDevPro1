package Main;

import Accounts.*;
import Bank.Bank;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

/**
 * BankLauncher handles interactions with the bank module, allowing login,
 * account management, and bank creation.
 */
public class BankLauncher {

    private final static ArrayList<Bank> banks = new ArrayList<>();
    private static Bank loggedBank;

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
    public static boolean isLogged() {
        return loggedBank != null;
    }

    /**
     * Adds a new bank to the list of registered banks.
     *
     * @param b The bank to be added.
     */
    public static void addBank(Bank b) { // TODO public access for temporary tests
        if (!banks.contains(b)) {
            banks.add(b);
            System.out.println("Bank successfully added: " + b.getBankName());
        }
    }

    /**
     * Handles the bank login process.
     //     */

    public static void bankLogin() {
        if (banks.isEmpty()) {
            System.out.println("No banks registered yet. Create a new bank first.");
            return;
        }

        Main.showMenuHeader("Bank Login Options");
        System.out.println("[1] Login by Bank Name");
        System.out.println("[2] Login by Bank ID");
        System.out.println("[3] Login by Bank Email");
        Main.setOption();

        Bank selectedBank = null;

        switch (Main.getOption()) {
            case 1 -> { // Login by Bank Name
                String bankName = Main.prompt("Enter Bank Name: ", false);
                selectedBank = getBank(Bank.BANK_NAME_COMPARATOR, new Bank(null, bankName));
            }
            case 2 -> { // Login by Bank ID
                Field<Integer, Integer> bankIdField = new Field<Integer, Integer>("Bank ID", Integer.class, 1, new Field.IntegerFieldValidator());
                bankIdField.setFieldValue("Enter Bank ID: ");
                selectedBank = getBank(Bank.BANK_ID_COMPARATOR, new Bank(bankIdField.getFieldValue(), null));
            }
            case 3 -> { // Login by Bank Credentials (ID + Name)
                Field<Integer, Integer> bankIdField = new Field<Integer, Integer>("Bank ID", Integer.class, 1, new Field.IntegerFieldValidator());
                bankIdField.setFieldValue("Enter Bank ID: ");
                int bankId = bankIdField.getFieldValue();
                String bankName = Main.prompt("Enter Bank Name: ", false);
                selectedBank = getBank(Bank.BANK_CREDENTIALS_COMPARATOR, new Bank(bankId, bankName));
            }
            default -> {
                System.out.println("Invalid option. Returning to main menu.");
                return;
            }
        }

        if (selectedBank != null) {
            setLogSession(selectedBank);
            System.out.println("Successfully logged into " + loggedBank.getBankName());
            bankInit();
        } else {
            System.out.println("Bank not found. Please try again.");
        }
    }

    /**
     * Displays a menu of all registered banks.
     */
    public static void showBanksMenu() {
        if (banks.isEmpty()) {
            System.out.println("No banks have been registered yet.");
            return;
        }

        System.out.println("List of Registered Banks:");
        for (int i = 0; i < banks.size(); i++) {
            System.out.println((i + 1) + ". " + banks.get(i).getBankName());
        }
    }

    /**
     * Finds an account by account number in all registered banks.
     *
     * @param accountNum The account number to search for.
     * @return The account if found, null otherwise.
     */
    public static Optional<Account> findAccount(String accountNum) {
        for (Bank bank : banks) {
            Optional<Account> account = bank.getBankAccount(accountNum);
            if (account.isPresent()) {
                return account;
            }
        }
        return Optional.empty();
    }

    /**
     * Retrieves a bank that matches the given comparator.
     *
     * @param bankComparator Comparator to determine the matching criteria.
     * @param bank Bank object to be compared.
     * @return The matching Bank object, or null if no match is found.
     */
    public static Bank getBank(Comparator<Bank> bankComparator, Bank bank) {
        return banks.stream()
                .filter(b -> bankComparator.compare(b, bank) == 0)
                .findFirst()
                .orElse(null);
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
            case 1 -> displayAccounts(CreditAccount.class);
            case 2 -> displayAccounts(SavingsAccount.class);
            case 3 -> displayAccounts(StudentAccount.class);
            case 4 -> displayAccounts(BusinessAccount.class);
            case 5 -> searchAccount();
            case 6 -> displayAllAccounts();
            default -> System.out.println("Invalid option. Try again.");
        }
    }

    /**
     * Searches for an account by its account number across all registered banks.
     * If found, displays the account details; otherwise, notifies the user that
     * the account was not found.
     */
    public static void searchAccount() {
        // Prompt user to enter an account number
        String accountNum = Main.prompt("Enter account number: ", false);

        // Search for the account using findAccount method
        Optional<Account> account = findAccount(accountNum);

        // Display account details if found, otherwise inform the user
        if (account.isPresent()) {
            System.out.println("Account found: " + account.get());
        } else {
            System.out.println("Account not found.");
        }
    }


    /**
     * Display all accounts registered under the logged-in bank.
     */
    private static void displayAllAccounts() {
        System.out.println("Showing all accounts:");
        loggedBank.showAccounts(null);
    }

    /**
     * Display accounts of a specific type (Credit or Savings).
     * @param accountType The class type of accounts to display.
     */
    private static void displayAccounts(Class<? extends Account> accountType) {
        System.out.println("Showing " + (accountType == CreditAccount.class ? "Credit" : "Savings") + " Accounts:");
        loggedBank.showAccounts(accountType);
    }


    /**
     * Handles the creation of a new account within the currently logged-in bank.
     */
    public static void newAccounts() {
        if (loggedBank == null) {
            System.out.println("No bank logged in.");
            return;
        }

        Main.showMenuHeader("Create a New Account");
        Main.showMenu(Menu.AccountTypeSelection.menuIdx);
        Main.setOption();

        switch (Main.getOption()) {
            case 1 -> System.out.println("Credit Account created: " + loggedBank.createNewCreditAccount());
            case 2 -> System.out.println("Savings Account created: " + loggedBank.createNewSavingsAccount());
            case 3 -> System.out.println("Student Account created: " + loggedBank.createNewStudentAccount());
            case 4 -> System.out.println("Business Account created: " + loggedBank.createNewBusinessAccount());
            default -> System.out.println("Invalid choice.");
        }
    }

    /**
     * Logs into a selected bank session.
     *
     * @param bank The bank to log into.
     */
    public static void setLogSession(Bank bank) {
        loggedBank = bank;
    }

    /**
     * Logs out from the current bank session.
     */
    private static void logout() {
        System.out.println("Logging out from " + loggedBank.getBankName());
        loggedBank = null;
    }

    /**
     * Creates a new bank and registers it in the system.
     */
    public static void createNewBank() {
        Field<String, Integer> bankNameField = new Field<String, Integer>("Bank Name", String.class, 3, new Field.StringFieldLengthValidator());
        bankNameField.setFieldValue("Enter Bank Name: ");

        Field<Integer, Integer> bankIdField = new Field<Integer, Integer>("Bank ID", Integer.class, 1, new Field.IntegerFieldValidator());
        bankIdField.setFieldValue("Enter Bank ID: ");

        Bank newBank = new Bank(bankIdField.getFieldValue(), bankNameField.getFieldValue());
        // Check if the bank already exists before adding
        if (isDuplicateBank(newBank)) {
            System.out.println("A bank with the same ID or Name already exists. Please try again.");
        } else {
            addBank(newBank);  // Uses the addBank() method
        }
    }

    /**
     * Checks if a bank with the same ID or name already exists in the system.
     *
     * @param newBank The bank object to check for duplicates.
     * @return true if a bank with the same ID or name exists, false otherwise.
     */
    private static boolean isDuplicateBank(Bank newBank) {
        return banks.stream().anyMatch(bank ->
                bank.getBankId() == newBank.getBankId() ||
                        bank.getBankName().equalsIgnoreCase(newBank.getBankName())
        );
    }

    /**
     * Returns the number of registered banks.
     *
     * @return The number of banks.
     */
    public static int bankSize() {
        return banks.size();
    }

    /**
     * Retrieves a bank by index.
     *
     * @param index The index of the bank.
     * @return Optional of Bank.
     */
    public static Optional<Bank> getBankByIndex(int index) {
        if (index > 0 && index <= banks.size()) {
            return Optional.of(banks.get(index - 1));
        }
        return Optional.empty();
    }

    public static ArrayList<Bank> getBanks() {
        return banks;
    }
}