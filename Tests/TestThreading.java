package Tests;

import Accounts.*;
import Bank.Bank;

import java.util.Random;

public class TestThreading {

    public static void main(String[] args) {
        Bank bank = new Bank(0, "TestBank", "1234");

        // Add sample accounts
        SavingsAccount acc1 = new SavingsAccount(bank, "SA001", "1234", "Alice", "Smith", "alice@bank.com", 50000);
        SavingsAccount acc2 = new SavingsAccount(bank, "SA002", "1234", "Bob", "Brown", "bob@bank.com", 30000);
        CreditAccount creditAcc = new CreditAccount(bank, "CA001", "1234", "Charlie", "Davis", "charlie@bank.com");
        BusinessAccount businessAcc = new BusinessAccount(bank, "BA001", "Diana", "King", "diana@company.com", "1234", 5000);
        StudentAccount studentAcc = new StudentAccount(bank, "ST001", "Eve", "Johnson", "eve@student.com", "1234", 2000);

        bank.addNewAccount(acc1);
        bank.addNewAccount(acc2);
        bank.addNewAccount(creditAcc);
        bank.addNewAccount(businessAcc);
        bank.addNewAccount(studentAcc);

        Random random = new Random();

        Runnable massDepositTask = () -> {
            for (int i = 0; i < 10; i++) {
                try {
                    double amount = 500 + random.nextInt(2000);
                    acc1.cashDeposit(amount);
                    System.out.println(Thread.currentThread().getName() + " [Deposit]: Deposited $" + amount + " to " + acc1.getAccountNumber());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        };

        Runnable massWithdrawalTask = () -> {
            for (int i = 0; i < 10; i++) {
                try {
                    double amount = 300 + random.nextInt(1000);
                    acc2.withdrawal(amount);
                    System.out.println(Thread.currentThread().getName() + " [Withdrawal]: Withdrew $" + amount + " from " + acc2.getAccountNumber());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        };

        Runnable massTransferTask = () -> {
            for (int i = 0; i < 10; i++) {
                try {
                    double amount = 200 + random.nextInt(1000);
                    acc1.transfer(acc2, amount);
                    System.out.println(Thread.currentThread().getName() + " [Transfer]: Transferred $" + amount + " from " + acc1.getAccountNumber() + " to " + acc2.getAccountNumber());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        };

        Runnable massCreditPaymentTask = () -> {
            for (int i = 0; i < 10; i++) {
                try {
                    double amount = 1500 + random.nextInt(4000);
                    creditAcc.pay(acc1, amount);
                    System.out.println(Thread.currentThread().getName() + " [Credit Payment]: Paid $" + amount + " from credit account to " + acc1.getAccountNumber());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        };

        Thread depositThread = new Thread(massDepositTask, "Deposit-Client");
        Thread withdrawalThread = new Thread(massWithdrawalTask, "Withdrawal-Client");
        Thread transferThread = new Thread(massTransferTask, "Transfer-Client");
        Thread creditThread = new Thread(massCreditPaymentTask, "CreditPayment-Client");

        depositThread.start();
        withdrawalThread.start();
        transferThread.start();
        creditThread.start();

        try {
            depositThread.join();
            withdrawalThread.join();
            transferThread.join();
            creditThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n=== MASS TRANSACTION HANDLING COMPLETE ===");
        System.out.println(acc1.getAccountBalanceStatement());
        System.out.println(acc2.getAccountBalanceStatement());
        System.out.println(creditAcc.getLoanStatement());

        System.out.println("\nTransaction Logs:");
        System.out.println(acc1.getTransactionsInfo());
        System.out.println(acc2.getTransactionsInfo());
    }
}
