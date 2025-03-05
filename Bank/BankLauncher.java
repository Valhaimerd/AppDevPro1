package Bank;

import Accounts.Account;

import java.util.ArrayList;
import java.util.Comparator;

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
    }

    private void showAccounts() {
        // TODO Complete this method.
    }

    private void newAccounts() {
        // TODO Complete this method.
    }
    public void bankLogin() {
        // TODO Complete this method.
    }

    private void setLogSession(Bank b) {
        // TODO Complete this method.
    }
    private void logout() {
        // TODO Complete this method.
    }

    public void createNewBank() {
        // TODO Complete this method.
    }
    public void showBanksMenu() {
        // TODO Complete this method.
    }

    private void addBank(Bank b) {
        // TODO Complete this method.
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
        return null;
    }

    public int bankSize() {
        // TODO Complete this method.
        return 0;
    }
}
