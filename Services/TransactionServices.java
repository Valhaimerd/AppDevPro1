package Services;

import Accounts.*;
import Bank.Bank;

public class TransactionServices {

    /**
     * Handles fund transfers between any two accounts.
     */
    public synchronized boolean transferFunds(Account sender, Account receiver, double amount) throws IllegalAccountType {
        if (!(sender instanceof SavingsAccount senderAcc) || !(receiver instanceof SavingsAccount receiverAcc)) {
            throw new IllegalAccountType("❌ Fund transfers can only be made between Savings Accounts.");
        }

        // Ensure StudentAccount transfer limit is respected
        if (senderAcc instanceof StudentAccount) {
            if (amount > StudentAccount.MAX_WITHDRAWAL_LIMIT) {
                System.out.println("❌ Transfer failed. Students have a $1000 limit.");
                return false;
            }
        }

        if (senderAcc.hasEnoughBalance(amount)) {
            senderAcc.insufficientBalance();
            return false;
        }

        senderAcc.adjustAccountBalance(-amount);
        receiverAcc.adjustAccountBalance(amount);

        senderAcc.addNewTransaction(senderAcc.getAccountNumber(), Transaction.Transactions.FUNDTRANSFER,
                "Transferred $" + amount + " to " + receiverAcc.getAccountNumber());
        receiverAcc.addNewTransaction(receiverAcc.getAccountNumber(), Transaction.Transactions.RECEIVE_TRANSFER,
                "Received $" + amount + " from " + senderAcc.getAccountNumber());

        return true;
    }

    /**
     * Handles fund transfers between different banks.
     */
    public synchronized boolean transferFunds(Account sender, Bank receiverBank, Account receiver, double amount) throws IllegalAccountType {
        if (!(sender instanceof SavingsAccount senderAcc) || !(receiver instanceof SavingsAccount receiverAcc)) {
            throw new IllegalAccountType("❌ Cross-bank transfers can only be made between Savings Accounts.");
        }

        double totalAmount = amount + senderAcc.getBank().getProcessingFee();

        if (senderAcc.hasEnoughBalance(totalAmount)) {
            senderAcc.insufficientBalance();
            return false;
        }

        senderAcc.adjustAccountBalance(-totalAmount);
        receiverAcc.adjustAccountBalance(amount);

        senderAcc.addNewTransaction(senderAcc.getAccountNumber(), Transaction.Transactions.EXTERNAL_TRANSFER,
                "External transfer of $" + amount + " to " + receiverAcc.getAccountNumber() + " in " + receiverBank.getName());
        receiverAcc.addNewTransaction(receiverAcc.getAccountNumber(), Transaction.Transactions.RECEIVE_TRANSFER,
                "Received $" + amount + " from " + senderAcc.getAccountNumber() + " in " + senderAcc.getBank().getName());

        return true;
    }

    /**
     * Handles deposits into any account type.
     */
    public synchronized boolean deposit(Account account, double amount) throws IllegalAccountType {
        if (!(account instanceof SavingsAccount savingsAcc)) {
            throw new IllegalAccountType("❌ Only Savings Accounts can receive deposits.");
        }

        if (amount > savingsAcc.getBank().getDepositLimit()) {
            System.out.println("❌ Deposit exceeds the bank's limit.");
            return false; // Ensure the deposit is rejected if over limit
        }

        savingsAcc.adjustAccountBalance(amount);
        savingsAcc.addNewTransaction(savingsAcc.getAccountNumber(), Transaction.Transactions.DEPOSIT,
                "Deposited $" + amount);
        return true;
    }

    /**
     * Handles withdrawals from any account type.
     */
    public synchronized boolean withdraw(Account account, double amount) throws IllegalAccountType {
        if (!(account instanceof SavingsAccount savingsAcc)) {
            throw new IllegalAccountType("❌ Only Savings Accounts can withdraw.");
        }

        if (savingsAcc instanceof StudentAccount && amount > StudentAccount.MAX_WITHDRAWAL_LIMIT) {
            System.out.println("❌ Withdrawal failed. Student withdrawal limit is $1000.");
            return false;
        }

        if (savingsAcc.hasEnoughBalance(amount)) {
            savingsAcc.insufficientBalance();
            return false;
        }

        savingsAcc.adjustAccountBalance(-amount);
        savingsAcc.addNewTransaction(savingsAcc.getAccountNumber(), Transaction.Transactions.WITHDRAWAL,
                "Withdrew $" + amount);

        return true;
    }

    public synchronized boolean creditPayment(Account creditAcc, Account savingsAcc, double amount) throws IllegalAccountType {
        if (!(creditAcc instanceof CreditAccount creditAccount) || !(savingsAcc instanceof SavingsAccount savingsAccount)) {
            throw new IllegalAccountType("❌ Credit payments can only be made from CreditAccount to SavingsAccount.");
        }

        if (!creditAccount.canCredit(amount)) {
            System.out.println("❌ Payment failed: Not enough credit available.");
            return false;
        }

        // FIX: Prevent double loan adjustment for BusinessAccount
        if (creditAcc instanceof BusinessAccount) {
            System.out.println("🔍 BusinessAccount detected, skipping loan adjustment.");
        } else {
            creditAccount.adjustLoanAmount(amount);
        }

        savingsAccount.adjustAccountBalance(amount);

        creditAccount.addNewTransaction(creditAccount.getAccountNumber(), Transaction.Transactions.PAYMENT,
                "Paid $" + amount + " to " + savingsAccount.getAccountNumber());
        savingsAccount.addNewTransaction(savingsAccount.getAccountNumber(), Transaction.Transactions.RECEIVE_TRANSFER,
                "Received $" + amount + " from Credit Account " + creditAccount.getAccountNumber());

        return true;
    }

    /**
     * Handles recompense (loan repayments) for a CreditAccount.
     */
    public synchronized boolean recompense(Account creditAcc, double amount) throws IllegalAccountType {
        if (!(creditAcc instanceof CreditAccount creditAccount)) {
            throw new IllegalAccountType("❌ Only Credit Accounts can make recompense payments.");
        }

        if (amount > creditAccount.getLoan()) {
            System.out.println("❌ Cannot recompense more than the outstanding loan.");
            return false;
        }

        creditAccount.adjustLoanAmount(-amount);

        return true;
    }
}