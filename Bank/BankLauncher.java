package Bank;

import Accounts.Account;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class BankLauncher {
    private static ArrayList<Bank> BANKS = new ArrayList<>();
    private Bank loggedBank = null;

    public boolean isLogged(){
        return false;
    }

     /**
     * Retrieves a bank by its name.
     * @param bankName The name of the bank to find.
     * @return The bank if found, otherwise null.
     */
     public Bank getBankByName(String bankName) {
        for (Bank bank : BANKS) {
            if (bank.getName().equalsIgnoreCase(bankName)) {
                return bank;
            }
        }
        System.out.println("No bank found with the name: " + bankName);
        return null;
    }

     /**
     * Initializes the bank system by creating a new bank.
     */
    public void bankInit() {
        // TODO Complete this method.
        createNewBank();
    }

    // Displays all accounts of the logged-in bank
    private void showAccounts() {
        // TODO Complete this method.
        if (!isLogged()) {
            System.out.println("No Bank is logged in.");
            return;
        }
        loggedBank.sortAccounts();
        loggedBank.showAccounts(Account.class);
    }

    // Creates new account in the logged-in bank
    private void newAccounts() {
        // TODO Complete this method.
        if (!isLogged()) {
            System.out.println("No bank is logged in.");
            return;
        }
        System.out.println("Choose account type: ");
        System.out.println("1. Credit Account");
        System.out.println("2. Savings Account");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        scanner.nextLine();

         switch (choice) {
            case 1 -> loggedBank.createNewCreditAccount();
            case 2 -> loggedBank.createNewSavingsAccount();
            default -> System.out.println("Invalid choice.");
         }
    }

    // Logs into an existing bank using its name and passcode
    public void bankLogin() {
        // TODO Complete this method.
         if (BANKS.isEmpty()) {
            System.out.println("No banks available. Create a bank first.");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter bank name: ");
        String name = scanner.nextLine();
        System.out.print("Enter passcode: ");
        String passcode = scanner.nextLine();

        for (Bank bank : BANKS) {
            if (bank.getName().equals(name) && bank.getPasscode().equals(passcode)) {
                setLogSession(bank);
                System.out.println("Logged into " + bank.getName());
                return;
            }
        }
        System.out.println("Invalid credentials.");
    }

     /**
     * Sets the logged-in bank session.
     * @param b The bank to log in to.
     */
    private void setLogSession(Bank b) {
        // TODO Complete this method.
        this.loggedBank = b;
    }

    // Logs out from the currently logged-in bank session
    private void logout() {
        // TODO Complete this method.
         if (isLogged()) {
            System.out.println("Logging out of " + loggedBank.getName());
            loggedBank = null;
        } else {
            System.out.println("No bank is logged in.");
        }
    }

    // Creates new bank and adds it on the list
    public void createNewBank() {
        // TODO Complete this method.
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Bank ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Bank Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Passcode: ");
        String passcode = scanner.nextLine();
        
        Bank newBank = new Bank(id, name, passcode);
        addBank(newBank);
        System.out.println("Bank created successfully!");
    }

    // Displays all available banks 
    public void showBanksMenu() {
        // TODO Complete this method.
        if (BANKS.isEmpty()) {
            System.out.println("No banks available.");
            return;
        }
        System.out.println("Available Banks:");
        for (Bank bank : BANKS) {
            System.out.println("- " + bank.getName());
        }
    }

    /**
     * Adds a new bank to the list if it does not already exist.
     * @param b The bank to add.
     */
    private void addBank(Bank b) {
        // TODO Complete this method.
        if (!BANKS.contains(b)) {
            BANKS.add(b);
        } else {
            System.out.println("Bank already exists.");
        }
    }

    /**
     * Retrieves a bank using a comparator.
     * @param comparator The comparator used to find the bank.
     * @param bank The bank object to compare against.
     * @return The found bank, or null if not found.
     */
    public Bank getBank(Comparator<Bank> comparator, Bank bank) {
        // TODO Complete this method.
        return BANKS.stream()
                .filter(b -> comparator.compare(b, bank) == 0)
                .findFirst()
                .orElse(null);
    }


     /**
     * Returns the list of all banks.
     * @return An ArrayList of all banks.
     */
    public static ArrayList<Bank> getBanks() {
        return BANKS;
    }


     /**
     * Finds an account by account number in the logged-in bank.
     * @param accountNum The account number to search for.
     * @return The found account, or null if no bank is logged in or account does not exist.
     */
    public Account findAccount(String accountNum) {
        // TODO Complete this method.
        if (!isLogged()) {
            System.out.println("No bank is logged in.");
            return null;
        }
        return loggedBank.getBankAccount(loggedBank, accountNum);

    }

    // Returns the total number of banks
    public int bankSize() {
        // TODO Complete this method.
        return BANKS.size();
    }
}
