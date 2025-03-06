package Accounts;

import Bank.Bank;

public class CreditAccount extends Account implements Payment, Recompense {
    private double loan;


    /**
     * Constructor for CreditAccount.
     * @param bank The bank associated with this account.
     * @param ACCOUNTNUMBER The account number of this credit account.
     * @param OWNERFNAME Owner's first name.
     * @param OWNERLNAME Owner's last name.
     * @param OWNEREMAIL Owner's email address.
     * @param pin The PIN for account security.
     * @param loan Initial loan amount.
     */
    public CreditAccount(Bank bank, String ACCOUNTNUMBER, String OWNERFNAME, String OWNERLNAME, String OWNEREMAIL, String pin, double loan) {
        super(bank, ACCOUNTNUMBER, OWNERFNAME, OWNERLNAME, OWNEREMAIL, pin);
        this.loan = loan;
    }

    /**
     * Returns the loan statement of this credit account.
     * @return A string representation of the loan amount.
     */
    public String getLoanStatement() {
        // TODO Complete this method. (done)
        return "Loan Amount: " + loan;
    }

    /**
     * Checks if the account can proceed with a credit transaction.
     * @param amountAdjustment The amount of credit to be adjusted.
     * @return True if the transaction can proceed, false otherwise.
     */
    private boolean canCredit(double amountAdjustment) {
        // TODO Complete this method. (done)
        return (loan + amountAdjustment) <= getBank().getCREDITLIMIT();
    }

    /**
     * Adjusts the loan amount, ensuring it does not go below zero.
     * @param amountAdjustment The amount to adjust the loan balance.
     */
    private void adjustLoanAmount(double amountAdjustment) {
        // TODO Complete this method. (done)
        this.loan = Math.max(0, this.loan + amountAdjustment);
    }

    /**
     * Pays an amount to another account.
     * @param account The target account to receive payment.
     * @param amount The amount to be paid.
     * @return True if the transaction was successful, false otherwise.
     * @throws IllegalAccountType if the target account is also a CreditAccount.
     */

    @Override
    public boolean pay(Account account, double amount) throws IllegalAccountType {
        // TODO Complete this method. (done)
        if (account instanceof CreditAccount) {
            throw new IllegalAccountType("Credit Accounts cannot pay to other Credit Accounts.");
        }

        if (this.loan >= amount) {
            this.loan -= amount;

            // Ensure the payment is only made to a SavingsAccount
            if (account instanceof SavingsAccount) {
                ((SavingsAccount) account).cashDeposit(amount);
            } else {
                throw new IllegalAccountType("Credit Accounts can only pay to Savings Accounts.");
            }

            this.addNewTransaction(account.getACCOUNTNUMBER(), Transaction.Transactions.Payment, "Payment of " + amount);
            return true;
        }

        return false;
    }

    /**
     * Recompenses some amount to reduce the loan balance.
     * @param amount The amount to recompense.
     * @return True if the recompense was successful, false otherwise.
     */
    @Override
    public boolean recompense(double amount) {
        // TODO Complete this method. (done)
        if (amount <= loan) {
            adjustLoanAmount(-amount);
            this.addNewTransaction(this.getACCOUNTNUMBER(), Transaction.Transactions.Recompense, "Recompensed " + amount);
            return true;
        }
        return false;
    }

    /**
     * Provides a string representation of the credit account.
     * @return A formatted string containing account details.
     */
    @Override
    public String toString() {
        // TODO Complete this method. (done)
        return "CreditAccount{" +
                "Account Number='" + getACCOUNTNUMBER() + '\'' +
                ", Owner='" + getOwnerFullName() + '\'' +
                ", Loan Amount=" + loan +
                '}';
    }
}
