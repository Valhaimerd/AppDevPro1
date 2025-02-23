package Accounts;

import Bank.Bank;

public class SavingsAccount extends Account implements Withdrawal, Deposit, FundTransfer {
    private double balance;

    public SavingsAccount(Bank bank, String ACCOUNTNUMBER, String OWNERFNAME, String OWNERLNAME, String OWNEREMAIL, String pin, double balance) {
        super(bank, ACCOUNTNUMBER, OWNERFNAME, OWNERLNAME, OWNEREMAIL, pin);
        this.balance = balance;
    }

    public String getAccountBalanceStatement() {
        // TODO Complete this method.
        return "";
    }

    private boolean hasEnoughBalance(double amount) {
        // TODO Complete this method.
        return true;
    }

    private void insufficientBalance() {
        // TODO Complete this method.
    }

    @Override
    public String toString() {
        // TODO Complete this method.
        return super.toString();
    }

    @Override
    public boolean cashDeposit(double amount) {
        // TODO Complete this method.
        return false;
    }

    @Override
    public boolean transfer(Bank bank, Account account, double amount) throws IllegalAccountType {
        // TODO Complete this method.
        return false;
    }

    @Override
    public boolean transfer(Account account, double amount) throws IllegalAccountType {
        // TODO Complete this method.
        return false;
    }

    @Override
    public boolean withdrawal(double amount) {
        // TODO Complete this method.
        return false;
    }
}
