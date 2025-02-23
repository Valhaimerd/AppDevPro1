package Accounts;

import Bank.Bank;

import java.util.ArrayList;

public abstract class Account {
    private Bank bank;
    private String ACCOUNTNUMBER;
    private String OWNERFNAME, OWNERLNAME, OWNEREMAIL;
    private String pin;
    private ArrayList<Transaction> TRANSACTIONS;

    public Account(Bank bank, String ACCOUNTNUMBER, String OWNERFNAME, String OWNERLNAME, String OWNEREMAIL, String pin) {
        this.bank = bank;
        this.ACCOUNTNUMBER = ACCOUNTNUMBER;
        this.OWNERFNAME = OWNERFNAME;
        this.OWNERLNAME = OWNERLNAME;
        this.OWNEREMAIL = OWNEREMAIL;
        this.pin = pin;
    }

    public Bank getBank() {
        return bank;
    }

    public String getACCOUNTNUMBER() {
        return ACCOUNTNUMBER;
    }

    public String getOWNERFNAME() {
        return OWNERFNAME;
    }

    public String getOWNERLNAME() {
        return OWNERLNAME;
    }

    public String getOWNEREMAIL() {
        return OWNEREMAIL;
    }

    public String getPin() {
        return pin;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public void setACCOUNTNUMBER(String ACCOUNTNUMBER) {
        this.ACCOUNTNUMBER = ACCOUNTNUMBER;
    }

    public void setOWNERFNAME(String OWNERFNAME) {
        this.OWNERFNAME = OWNERFNAME;
    }

    public void setOWNERLNAME(String OWNERLNAME) {
        this.OWNERLNAME = OWNERLNAME;
    }

    public void setOWNEREMAIL(String OWNEREMAIL) {
        this.OWNEREMAIL = OWNEREMAIL;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getFullName() {return OWNERLNAME + ", " + OWNERFNAME;}

    public void addNewTransaction(String accountNum, Transaction.Transactions type, String description) {
        // TODO Complete this method.
    }

    public String getTransactionInfo() {
        // TODO Complete this method.
        return "";
    }

    @Override
    public String toString() {
        // TODO Complete this method.
        return "";
    }
}
