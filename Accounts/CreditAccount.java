package Accounts;

import Bank.Bank;

public class CreditAccount extends Account implements Payment, Recompense {
    private double loan;


    public CreditAccount(Bank bank, String ACCOUNTNUMBER, String OWNERFNAME, String OWNERLNAME, String OWNEREMAIL, String pin, double loan) {
        super(bank, ACCOUNTNUMBER, OWNERFNAME, OWNERLNAME, OWNEREMAIL, pin);
        this.loan = loan;
    }

    public String getLoanStatement() {
        // TODO Complete this method.
        return "";
    }

    private boolean canCredit(double amountAdjustment) {
        // TODO Complete this method.
        return true;
    }

    private void adjustLoanAmount(double amountAdjustment) {
        // TODO Complete this method.
    }

    @Override
    public String toString() {
        // TODO Complete this method.
        return super.toString();
    }

    @Override
    public boolean pay(Account account, double amount) throws IllegalAccountType {
        // TODO Complete this method.
        return false;
    }

    @Override
    public boolean recompense(double amount) {
        // TODO Complete this method.
        return false;
    }
}
