package Accounts;

import Bank.Bank;

public class SavingsAccount extends Account implements Withdrawal, Deposit, FundTransfer {
    private double balance;

    public SavingsAccount(Bank bank, String ACCOUNTNUMBER, String OWNERFNAME, String OWNERLNAME, String OWNEREMAIL, String pin, double balance) {
        super(bank, ACCOUNTNUMBER, OWNERFNAME, OWNERLNAME, OWNEREMAIL, pin);
        this.balance = balance;
    }

    /**
     * Validates whether this savings account has enough balance to proceed.
     * @param amount Amount to be adjusted.
     * @return Flag if transaction can proceed.
     */
    public boolean hasEnoughBalance(double amount) {
        // Logically it is correct ignore the warning
        // TODO Complete this method (done)
        return this.balance >= amount;
    }

    /**
     * Transfers an amount of money from this account to another savings account.
     * @param account Account number of recipient.
     * @param amount Amount to be transferred.
     * @return Flag if transfer is successful.
     * @throws IllegalAccountType If transfer is attempted to a CreditAccount.
     */
    @Override
    public boolean transfer(Account account, double amount) throws IllegalAccountType {
        // TODO Complete this method (done)
        if (!(account instanceof SavingsAccount)) {
            throw new IllegalAccountType("Cannot transfer to a Credit Account.");
        }
        if (!hasEnoughBalance(amount)) {
            insufficientBalance();
            return false;
        }
        this.balance -= amount;
        ((SavingsAccount) account).adjustAccountBalance(amount);
        addNewTransaction(account.getACCOUNTNUMBER(), Transaction.Transactions.FundTransfer, "Transferred " + amount);
        return true;
    }

    /**
     * Transfers an amount of money from this account to another savings account in a different bank.
     * @param bank Bank object of the recipient.
     * @param account Account number of recipient.
     * @param amount Amount to be transferred.
     * @return Flag if transfer is successful.
     * @throws IllegalAccountType If transfer is attempted to a CreditAccount.
     */
    @Override
    public boolean transfer(Bank bank, Account account, double amount) throws IllegalAccountType {
        // TODO Complete this method (done)
        if (!(account instanceof SavingsAccount)) {
            throw new IllegalAccountType("Cannot transfer to a Credit Account.");
        }
        if (!hasEnoughBalance(amount + bank.getProcessingFee())) {
            insufficientBalance();
            return false;
        }
        this.balance -= (amount + bank.getProcessingFee());
        ((SavingsAccount) account).adjustAccountBalance(amount);
        addNewTransaction(account.getACCOUNTNUMBER(), Transaction.Transactions.FundTransfer,
                "Transferred " + amount + " to bank: " + bank.getName());
        return true;
    }

    /**
     * Deposits some cash into this account.
     * @param amount Amount to be deposited.
     * @return Flag if deposit is successful.
     */
    @Override
    public boolean cashDeposit(double amount) {
        // TODO Complete this method (done)
        if (amount <= 0 || amount > this.getBank().getDEPOSITLIMIT()) {
            System.out.println("Deposit failed: Amount exceeds bank deposit limit.");
            return false;
        }
        this.balance += amount;
        addNewTransaction(this.getACCOUNTNUMBER(), Transaction.Transactions.Deposit, "Deposited " + amount);
        return true;
    }

    /**
     * Withdraws an amount of money from this savings account.
     * @param amount Amount to be withdrawn.
     * @return Flag if withdrawal is successful.
     */
    @Override
    public boolean withdrawal(double amount) {
        // TODO Complete this method (done)
        if (amount <= 0 || amount > this.getBank().getWITHDRAWLIMIT()) {
            System.out.println("Withdrawal failed: Amount exceeds withdrawal limit.");
            return false;
        }
        if (!hasEnoughBalance(amount)) {
            insufficientBalance();
            return false;
        }
        this.balance -= amount;
        addNewTransaction(this.getACCOUNTNUMBER(), Transaction.Transactions.Withdraw, "Withdrew " + amount);
        return true;
    }

    /**
     * Retrieves the account balance statement.
     * @return String balance statement.
     */
    public String getAccountBalanceStatement() {
        // TODO Complete this method (done)
        return "Account Balance: " + this.balance;
    }

    /**
     * Warns the account owner that their balance is insufficient for the transaction.
     */
    private void insufficientBalance() {
        // TODO Complete this method (done)
        System.out.println("Transaction failed: Insufficient balance.");
    }

    /**
     * Adjusts the account balance of this savings account based on the amount.
     * @param amount Amount to be added or subtracted.
     */
    public void adjustAccountBalance(double amount) {
        // TODO Complete this method (done)
        this.balance += amount;
        if (this.balance < 0) {
            this.balance = 0;
        }
    }

    @Override
    public String toString() {
        return "SavingsAccount{accountNumber=" + getACCOUNTNUMBER() + ", balance=" + balance + "}";
    }
}