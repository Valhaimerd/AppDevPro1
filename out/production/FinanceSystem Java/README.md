# Banking System  

The **Banking System Program** is designed to simulate a real-world banking system where users can create and manage bank accounts. This project helps understand how banking applications work while improving programming skills by implementing **Object-Oriented Programming (OOP) concepts, UML diagrams, and SOLID principles**.  

---
## **Core Functionalities**  
## **CLASSES**  

### **Account Class**  
- **Stores Account Information** – Holds bank details, account numbr, and owner's details (name, email, PIN).  
- **Keeps Track of Transactions** – Maintains a list of past transactions related to the account.  

### Business Class
- **Higher Credit Limit** – Business accounts have twice the credit limit of regular credit accounts.
- **Business Loan Management** – Allows businesses to take and adjhust loans within the increased credit limit.
- **Restricted Payments** – Can only make payments to Savings Accounts to ensure business transactions follow proper banking rules.
- **Recompense Transactions** – Supports loan repayments with higher limits than standard credit accounts.
- **Transaction Processing** – Uses *TransactionServices* to handle paymenmts and recompense securely.

### **Credit Class**
- **Credit-Based Transactions** – Allows users to perform transactions using borrowed money (credit).
- **Loan Balance Management** – Keeps track of the outstanding loan balance and ensures it does not fall below zero.
- **Credit Limit Enforcement** – Prevents transactions that exceed the maximum credit limit set by the bank.
- **Payments to Savings Accounts** – Can only transfer money to Savings Accounts, not other CreditAccounts.
- **Recompense (Loan Repayment)** – Supports repaying the bank to reduce the loan balance.
- **Loan Statement Generation** – Provides a formstted loan summary, showing the account holder and remaining loan balance.

### **Illegal Account Type Class**
- **Stops invalid transactions** – It prevents actions between accounts that shouldn’t interact (example is when a credit account sending money to another credit account).
- **Shows error messages** – When a wrong transaction happens, it gives a clear message about the mistake.
- **Helps keep the program safe** – It makes sure only valid transactions happen, avoiding errors in the system.

### **Savings Account Class**
- **Tracks balance** – Keeps a record of how much money is in the account.
- **Checks available funds** – Ensures there’s enough money before allowing a transaction.
- **Deposits money** – Adds money to the account.
- **Withdraws money** – Takes money out of the account if there’s enough balance.
- **Transfers funds** – Sends money to another savings account (within the same bank or a different bank).
- **Prevents negative balance** – Stops the balance from going below zero.
- **Shows account details** – Provides a summary of the account balance and ownre details.

### **Student Account class**
- **Inherits savings features** – Works like a savings account with deposits, withdrawals, and transfers.
- **Limits withdrawals** – Has a maximum withdrawal limit of ₱1000 to control spending.
- **Manages fund transfers** – Allows transfers but restricts large amounts.
- **Processes transactions safely** – Uses TrsansactionServices to handle withdrawals and transfers properly.
- **Provides account details** – Shows account balsnce, owner info, and withdrawal limits.

### **Bank Class**

## **CLASS LAUNCHERS**  
### **Account Launcher Class**

### **Bank Launcher Class**

### **Bussiness Account Launcher Class**

### **Credit Account Launcher Class** 

### **Savings Account Launcher Class**

### **Student Account Launcher Class**


## **SERVICES**  

### **AccountService**

### **BankService**

### **Deposit**

### **FundTransfer**

### **LogServeice**

### **Payment**

### **Recompense**

### **ServiceProvider**

### **Transaction**

### **TransactionServices**

### **Withdrawal**

---

## **Documentation**  

### **Added Methods/Functions**  

### **Account.java**  
1. **`getTransactionsInfo()`**  
   -  

2. **`getOwnerFullName()`**  
   -  

3. **`getOwnerEmail()`**  
   -  

4. **Transaction Notif()**  
   -  

5. **Email Validator()**  
   -  

6. **`getTransactions()`**  
   -  

### **AccountLauncher.java**  
1. **`getAssocBank()`**  
   -  

### **CreditAccount.java**  
1. **`pay()`**  
   -  

2. **`recompense()`**  
   -  

### **SavingsAccount.java**  
1. **`transfer(account: Account, amount: double)`**  
   -  

2. **`transfer(bank: Bank, account: Account, amount: double)`**  
   -  

3. **`cashDeposit()`**  
   -  

4. **`withdrawal()`**  
   -  

### **Bank.java**  
1. **`accountNumberCounter()`**  
   -  

2. **`ACCOUNT_NUMBERS: Set<String> sortAccounts(): void`**  
   -  

3. **`getBankName(): String`**  
   -  

---

📌 **Note:** More details will be added later.
