package Accounts;

import Bank.Bank;
import Services.TransactionServices;

/**
 * BusinessAccount class representing a business credit account with higher limits
 * and business-focused transactions.
 */
public class BusinessAccount extends CreditAccount {

    private final TransactionServices transactionService = new TransactionServices();
    private static final double BUSINESS_CREDIT_LIMIT_MULTIPLIER = 2.0; // Business credit is usually higher

    /**
     * Constructor for BusinessAccount.
     *
     * @param bank          The bank associated with this business account.
     * @param accountNumber The unique account number.
     * @param ownerFname    Owner's first name.
     * @param ownerLname    Owner's last name.
     * @param email         Owner's email address.
     * @param pin           Security PIN for authentication.
     * @param initialLoan   Initial loan balance (if any).
     */
    public BusinessAccount(Bank bank, String accountNumber, String ownerFname, String ownerLname,
                           String email, String pin, double initialLoan) {
        super(bank, accountNumber, ownerFname, ownerLname, email, pin);

        if (initialLoan > 0 && canBusinessCredit(initialLoan)) {
            super.adjustLoanAmount(initialLoan);
        } else {
            System.out.println("⚠️ Initial loan amount exceeds business credit limit.");
        }
    }

    /**
     * Gets the credit limit for the Business Account.
     *
     * @return The business credit limit.
     */
    public double getBusinessCreditLimit() {
        return super.getBank().getCreditLimit() * BUSINESS_CREDIT_LIMIT_MULTIPLIER;
    }
    /**
     * Determines whether the business account can take additional credit.
     *
     * @param amount The amount to check.
     * @return True if within credit limit, otherwise false.
     */
    public boolean canBusinessCredit(double amount) {
        return (getLoan() + amount) <= getBusinessCreditLimit();
    }

    /**
     * Makes a business payment to a savings account.
     *
     * @param recipient The recipient account.
     * @param amount    The amount to pay.
     * @return True if successful, false otherwise.
     */
    public synchronized boolean pay(Account recipient, double amount) throws IllegalAccountType {
        if (!(recipient instanceof SavingsAccount savingsRecipient)) {
            throw new IllegalArgumentException("Business accounts can only pay to Savings Accounts.");
        }

        if (!canBusinessCredit(amount)) { // Business accounts may have higher limits
            System.out.println("Payment failed: Business credit limit exceeded.");
            return false;
        }

        return transactionService.creditPayment(this, savingsRecipient, amount);
    }

    /**
     * Business accounts can recompense higher amounts than standard credit accounts.
     *
     * @param amount The amount to recompense.
     * @return True if successful, false otherwise.
     */
    @Override
    public synchronized boolean recompense(double amount) throws IllegalAccountType {
        return transactionService.recompense(this, amount);
    }

    @Override
    public void adjustLoanAmount(double amount) {
        double currentLoan = getLoan();
        double newLoan = currentLoan + amount;

        if (newLoan > getBusinessCreditLimit()) {
            System.out.println("❌ Loan adjustment failed: Exceeds business credit limit.");
            return;
        }

        super.adjustLoanAmount(amount);
        System.out.println("✅ Loan successfully adjusted. New Loan: " + getLoan());
    }

}