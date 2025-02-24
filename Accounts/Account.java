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
        this.TRANSACTIONS = new ArrayList<>();
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


    /**
    * Adds a new transaction to the account's transaction history.
    *
    * @param accountNum  The account number associated with the transaction.
    * @param type        The type of transaction (e.g., deposit, withdrawal).
    * @param description A brief description of the transaction.
    */
    public void addNewTransaction(String accountNum, Transaction.Transactions type, String description) {
        // TODO Complete this method.
        Transaction transaction = new Transaction(accountNum, type, description);
        TRANSACTIONS.add(transaction);
    }

    
    /**
    * Retrieves the transaction history of the account.
    *
    * @return A string representation of all transactions, or a message indicating no transactions are available.
    */
    public String getTransactionInfo() {
        // TODO Complete this method.
        if (TRANSACTIONS.isEmpty()) {
            return "No transactions available.";
        }
        StringBuilder transactionInfo = new StringBuilder("Transaction History:\n");
        for (Transaction t : TRANSACTIONS) { // Fixed loop variable type
            transactionInfo.append(t.toString()).append("\n");
        }
        return transactionInfo.toString();
    }


    /**
    * Returns a string representation of the account details, including bank name, 
    * account number, owner's full name, email, and the number of transactions.
    *
    * @return A formatted string with account details.
    */
    @Override
    public String toString() {
        // TODO Complete this method.
        return "Account Details:\n" +
                "Bank: " + bank.getName() + "\n" +
                "Account Number: " + ACCOUNTNUMBER + "\n" +
                "Owner: " + getFullName() + "\n" +
                "Email: " + OWNEREMAIL + "\n" +
                "Transactions: " + TRANSACTIONS.size();
    }
}
