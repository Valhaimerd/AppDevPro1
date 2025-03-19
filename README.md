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
1. **loadTransactionsFromDatabase()**  
   - It clears the existing transaction list to avoid duplicates, fetches transactions from a log service using the account number, adds them back into the list, and prints the number of transactions loaded.
#### Why it was Added
   - To keep an account’s transaction history updated by automatically retrieving past transactions from a database. This amkes sure that it has accurate financial records without manual entry.

### **CreditAccount.java**  
1. **pay()**  
   -  allows a CreditAccount to send money to a SavingsAccount by checking if the credit limit allows the transaction. If the limit is exceeded, the payment fails. Otherwise, it processes the transfer through transactionService.creditPayment().
#### Why it was Added
   -  To allow credit accounts to make controlled payments while ensuring they do not exceed their borrowing limit.

2. **recompense()**  
   - It enables the CreditAccount to repay its loan balance by calling transactionService.recompense().
#### Why it was Added
   - To provide a way for users to settle their debts and restore available credit.

### **SavingsAccount.java**  
1. **transfer(account: Account, amount: double)**  
   -  allows a **SavingsAccount** to send money to another account within the same bank. It calls _transactionService.transferFunds()_ to process the transaction.
#### Why it was Added
   -  It was added so users can transafer funds between accounts without needing to withdraw cash manually.

2. **transfer(bank: Bank, account: Account, amount: double)**  
   -  enables a **SavingsAccount** to send money to an account in a different bank, applying a processing fee. It also calls _transactionService.transferFunds()_.
#### Why it was Added
   -  We added it to support interbank transfers, allowing users to send money outside their bank while ensuring feeas are applied.

3. **cashDeposit()**  
   -  allows a **SavingsAccount** to receive money by calling _transactionService.deposit()_.
#### Why it was Added
   -  So users can increase their account balance by depositing cash into their savings account.

4. **withdrawal()**
   - lets a **SavingsAccount** take out money by calling _transactionService.withdraw()_.
#### Why it was Added
   - was added so users can access their funds when needed, ensuring they can withdraw cash from their account.
5. **getAccountBalance()** 
   - returns the current balance of a **SavingsAccount**.
#### Why it was Added
   - was added to let users check how much money they have in their account.

### **Bank.java**  
1. **passcode** (Attribute)
   - Prevents unauthorized access to the bank’s data.
#### Why it was Added
   - is added to provide a security layer for the bank and to ensure only authorized users can access or modify bank details.

2. **withdrawLimit** (Attribute)
   - Helps regulate cash flow in the bank and it also ensures users to not withdraw excessive amounts that could lead to insufficient funds.
#### Why it was Added
   - is added to set a maximum amount a user can withdraw from their account at a time. To prevent large withdrawals that could cause financial instability.

3. **creditLimit** (Attribute)
   - it helps manage the bank’s risk by limiting excessive credit borrowing.
#### Why it was Added
   - was added to define the maximum credit (loan) a user can borrow. To prevent users from borrowing beyond what the bank can handle.


### **CreditAccountLauncher**
1.

### **SavingsAccountLauncher**
1.

### **BankLauncher**
1. **displayAllAccounts()**
   - It calls loggedBank.showAccounts(null), which displays every account in the bank, regardless of whether it is a credit, savings, student, or business account.
#### Why it was added
   - This function was added to provide a quick way to view all accounts under the currently logged-in bank without filtering by account type.

2. **displayAccounts(Class<? extends Account> accountType)**
   - It takes a class type as a parameter (CreditAccount.class, SavingsAccount.class, etc.) and calls loggedBank.showAccounts(accountType), displaying only accounts of the given type.
#### Why it was added
   - This function allows filtering accounts by type (Credit, Savings, etc.), making it easier to manage and view specific types of accounts.

3. **getBankByIndex(int index)**
   - It checks if the given index is valid and returns the corresponding bank wrapped in an Optional. If the index is out of range, it returns an empty Optional.
#### Why it was added
   - This function simplifies retrieving a bank using an index, which is useful for menus where banks are listed numerically.

4. **getBanks()**
   - It returns the banks list, allowing other parts of the program to access and manipulate the registered banks.
#### Why it was added
   - This function was added to provide access to the full list of registered banks, making it easier to retrieve all banks when needed.


---

📌 **Note:** More details will be added later.
