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

    public Bank getBankById(int bankID) {
        // TODO Complete this method.
        return null;
    }



    public void bankInit() {
        // TODO Complete this method.
        createNewBank();
    }

    private void showAccounts() {
        // TODO Complete this method.
        if (!isLogged()) {
            System.out.println("No Bank is logged in.");
            return;
        }
        loggedBank.sortAccounts();
        loggedBank.showAccounts(Account.class);
    }

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

    private void setLogSession(Bank b) {
        // TODO Complete this method.
        this.loggedBank = b;
    }
    private void logout() {
        // TODO Complete this method.
         if (isLogged()) {
            System.out.println("Logging out of " + loggedBank.getName());
            loggedBank = null;
        } else {
            System.out.println("No bank is logged in.");
        }
    }

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

    private void addBank(Bank b) {
        // TODO Complete this method.
        if (!BANKS.contains(b)) {
            BANKS.add(b);
        } else {
            System.out.println("Bank already exists.");
        }
    }

    public Bank getBank(Comparator<Bank> comparator, Bank bank) {
        // TODO Complete this method.
        return BANKS.stream()
                .filter(b -> comparator.compare(b, bank) == 0)
                .findFirst()
                .orElse(null);
    }

    public static ArrayList<Bank> getBanks() {
        return BANKS;
    }

    public Account findAccount(String accountNum) {
        // TODO Complete this method.
        if (!isLogged()) {
            System.out.println("No bank is logged in.");
            return null;
        }
        return loggedBank.getBankAccount(loggedBank, accountNum);

    }

    public int bankSize() {
        // TODO Complete this method.
        return BANKS.size();
    }
}
