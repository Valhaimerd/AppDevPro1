package Services;

import Accounts.*;
import Bank.Bank;

public class TransactionServices {
    LogService logService = ServiceProvider.getTransactionService();
    AccountService accountService = ServiceProvider.getAccountService();
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
        // Save changes in database:

        // Persist updated balances
        accountService.updateBalance(senderAcc, senderAcc.getAccountBalance());
        accountService.updateBalance(receiverAcc, receiverAcc.getAccountBalance());

        // Log transactions in DB
        logService.logTransaction(
                senderAcc.getAccountNumber(), receiverAcc.getAccountNumber(),
                String.valueOf(Transaction.Transactions.FUNDTRANSFER), amount,
                "Transferred $" + amount + " to " + receiverAcc.getAccountNumber()
        );

        logService.logTransaction(
                receiverAcc.getAccountNumber(), senderAcc.getAccountNumber(),
                String.valueOf(Transaction.Transactions.RECEIVE_TRANSFER), amount,
                "Received $" + amount + " from " + senderAcc.getAccountNumber()
        );

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

        if (!senderAcc.hasEnoughBalance(totalAmount)) {
            senderAcc.insufficientBalance();
            return false;
        }

        // Adjust balances locally
        senderAcc.adjustAccountBalance(-totalAmount);
        receiverAcc.adjustAccountBalance(amount);

        // Persist updated balances in the database
        accountService.updateBalance(senderAcc, senderAcc.getAccountBalance());
        accountService.updateBalance(receiverAcc, receiverAcc.getAccountBalance());

        // Log transactions in the database
        logService.logTransaction(
                senderAcc.getAccountNumber(),
                receiverAcc.getAccountNumber(),
                String.valueOf(Transaction.Transactions.EXTERNAL_TRANSFER),
                totalAmount,
                "External transfer of $" + amount + " to " + receiverAcc.getAccountNumber() + " in " + receiverBank.getName()
        );

        logService.logTransaction(
                receiverAcc.getAccountNumber(),
                senderAcc.getAccountNumber(),
                String.valueOf(Transaction.Transactions.RECEIVE_TRANSFER),
                amount,
                "Received $" + amount + " from " + senderAcc.getAccountNumber() + " in " + senderAcc.getBank().getName()
        );

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
            return false;
        }

        // Update balance locally
        savingsAcc.adjustAccountBalance(amount);
        savingsAcc.addNewTransaction(
                savingsAcc.getAccountNumber(),
                Transaction.Transactions.DEPOSIT,
                "Deposited $" + amount
        );

        // Persist changes in database
        accountService.updateBalance(savingsAcc, savingsAcc.getAccountBalance());
        logService.logTransaction(
                null,  // or null if there's no source account
                savingsAcc.getAccountNumber(),
                String.valueOf(Transaction.Transactions.DEPOSIT),
                amount,
                "Deposited $" + amount + " to account " + savingsAcc.getAccountNumber()
        );

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

        // Update local balance
        savingsAcc.adjustAccountBalance(-amount);
        savingsAcc.addNewTransaction(
                savingsAcc.getAccountNumber(),
                Transaction.Transactions.WITHDRAWAL,
                "Withdrew $" + amount
        );

        // Save updated balance in the database
        accountService.updateBalance(savingsAcc, savingsAcc.getAccountBalance());

        // Log the withdrawal transaction
        logService.logTransaction(
                savingsAcc.getAccountNumber(),
                "SYSTEM",   // No target account for withdrawal
                String.valueOf(Transaction.Transactions.WITHDRAWAL),
                amount,
                "Withdrew $" + amount + " from account " + savingsAcc.getAccountNumber()
        );

        return true;
    }

    /**
     * Handles credit payments from a CreditAccount to a SavingsAccount.
     */
//    public synchronized boolean creditPayment(Account creditAcc, Account savingsAcc, double amount) throws IllegalAccountType {
//        if (!(creditAcc instanceof CreditAccount creditAccount) || !(savingsAcc instanceof SavingsAccount savingsAccount)) {
//            throw new IllegalAccountType("❌ Credit payments can only be made from CreditAccount to SavingsAccount.");
//        }
//
//        if (!creditAccount.canCredit(amount)) {
//            System.out.println("❌ Payment failed: Not enough credit available.");
//            return false;
//        }
//
//        creditAccount.adjustLoanAmount(amount);
//        savingsAccount.adjustAccountBalance(amount);
//
//        creditAccount.addNewTransaction(creditAccount.getAccountNumber(), Transaction.Transactions.PAYMENT,
//                "Paid $" + amount + " to " + savingsAccount.getAccountNumber());
//        savingsAccount.addNewTransaction(savingsAccount.getAccountNumber(), Transaction.Transactions.RECEIVE_TRANSFER,
//                "Received $" + amount + " from Credit Account " + creditAccount.getAccountNumber());
//
//        return true;
//    }
    public synchronized boolean creditPayment(Account creditAcc, Account savingsAcc, double amount) throws IllegalAccountType {
        if (!(creditAcc instanceof CreditAccount creditAccount) || !(savingsAcc instanceof SavingsAccount savingsAccount)) {
            throw new IllegalAccountType("❌ Credit payments can only be made from CreditAccount to SavingsAccount.");
        }

        if (!creditAccount.canCredit(amount)) {
            System.out.println("❌ Payment failed: Not enough credit available.");
            return false;
        }

        if (creditAcc instanceof BusinessAccount) {
            System.out.println("⚠️ BusinessAccount detected, skipping loan adjustment.");
        } else {
            creditAccount.adjustLoanAmount(amount);
        }

        savingsAccount.adjustAccountBalance(amount);

        // Add local transactions
        creditAccount.addNewTransaction(creditAccount.getAccountNumber(), Transaction.Transactions.PAYMENT,
                "Paid $" + amount + " to " + savingsAccount.getAccountNumber());
        savingsAccount.addNewTransaction(savingsAccount.getAccountNumber(), Transaction.Transactions.RECEIVE_TRANSFER,
                "Received $" + amount + " from Credit Account " + creditAccount.getAccountNumber());

        // Update balances in SQL
        accountService.updateBalance(savingsAccount, savingsAccount.getAccountBalance());

        // Log transactions in SQL
        logService.logTransaction(
                creditAccount.getAccountNumber(),
                savingsAccount.getAccountNumber(),
                String.valueOf(Transaction.Transactions.PAYMENT),
                amount,
                "Credit payment of $" + amount + " to " + savingsAccount.getAccountNumber()
        );

        logService.logTransaction(
                savingsAccount.getAccountNumber(),
                creditAccount.getAccountNumber(),
                String.valueOf(Transaction.Transactions.RECEIVE_TRANSFER),
                amount,
                "Received credit payment of $" + amount + " from " + creditAccount.getAccountNumber()
        );

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

        // Persist loan update in SQL
        accountService.updateLoan(creditAccount, creditAccount.getLoan());

        return true;
    }
}
