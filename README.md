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
- **User Authentication** – Verifies user credentials by checking the entered account number and PIN against the selected bank's records.
- **Bank Selection** – Allows users to choose a bank before logging into an account.
- **Account Type Selection** – Provides an option to select between different account types such as savings, credit, student, or business accounts.
- **Session Management** – Stores the logged-in account details, provides access to them, and enables users to log out when needed.
- **Navigation to Account Menus** – Redirects authenticated users to the appropriate account-specific interface based on their account type.
### **Bank Launcher Class**
- **Account Management** – Loads accounts from the database, displays different account types, and facilitates new account creation.
- **Account Search** – Finds specific accounts across all banks using account numbers.
- **Account Type Selection** – Allows users to choose between Credit, Savings, Student, or Business accounts.
- **Bank Management** – Loads banks from the database, manages registered banks, and allows new bank creation with optional custom transaction limits.
- **Bank Authentication** – Verifies bank credentials during login using the bank name and passcode.
- **Bank Selection** – Displays a list of registered banks and allows users to select one for login.
- **Navigation to Account Menus** – Directs authenticated users to account-specific menus for further actions.
- **Session Management** – Maintains the current bank session, tracks if a bank is logged in, and enables logging out.
- **Transaction Management** – Loads and manages transaction history for all accounts in registered banks.
### **Bussiness Account Launcher Class**
- **Business Account Menu Navigation** – Provides a menu interface for business account operations after login.
- **Loan Statement Viewing** – Displays the current loan statement for the logged-in business account.
- **Business Payment Processing** – Allows making payments from the business account to a recipient savings account, ensuring compliance with credit limits.
- **Recompense Processing** – Facilitates loan repayment by validating amounts against the outstanding balance.
- **Transaction History Viewing** – Displays all past transactions associated with the business account.
- **Session Management** – Ensures that a business account is logged in before allowing access to features.
### **Credit Account Launcher Class** 
- **Credit Account Menu Navigation** – Provides a menu interface for credit account operations after login.
- **Loan Statement Viewing** – Displays the current loan statement for the logged-in credit account.
- **Credit Payment Processing** – Allows making payments from the credit account to a recipient savings account, ensuring sufficient credit availability.
- **Credit Recompense Processing** – Facilitates loan repayment by validating the entered amount against the outstanding loan balance.
- **Transaction History Viewing** – Displays all past transactions associated with the credit account.
- **Session Management** – Ensures that a credit account is logged in before allowing access to features.
### **Savings Account Launcher Class**
- **Savings Account Menu Navigation** – Provides a menu interface for savings account operations after login.
- **Account Balance Viewing** – Displays the current balance of the logged-in savings account.
- **Deposit Processing** – Allows users to deposit money into their savings account while ensuring valid amounts.
- **Withdrawal Processing** – Enables users to withdraw money, subject to available balance and withdrawal limits.
- **Fund Transfer**
  - **Internal Transfer** – Transfers funds between accounts within the same bank.
  - **External Transfer** – Transfers funds to an account in a different bank, with a processing fee applied.
- **Transaction History Viewing** – Displays all past transactions associated with the savings account.
- **Session Management** – Ensures that a savings account is logged in before allowing access to features.
### **Student Account Launcher Class**
- **Student Account Menu Navigation** – Provides a menu interface tailored for student accounts.
- **Account Balance Viewing** – Displays the current balance of the logged-in student account.
- **Restricted Withdrawals** – Enforces a student withdrawal limit of $1000 per transaction.
- **Limited Fund Transfers** – Allows fund transfers but limits the transfer amount to $1000.
- **Transaction History Viewing** – Displays past transactions related to the student account.
- **Session Management** – Ensures that a student account is logged in before allowing access to features.

## **SERVICES**  

### **AccountService**
- **Fetches all accounts** – Retrieves all accounts from the database.
- **Provides static fetch method** – Allows fetching accounts without needing an instance of _AccountService_.
- **Creates accounts** – Validates inputs and adds new accounts to the database.
- **Checks account balance** – Retrieves the balance of a specific account.
- **Updates account balance **– Modifies the balance of an account based on transactions.
- **Handles loan updates** – Adjusts balances for credit or loan accounts.

### **BankService**
- **Fetches all banks** – Retrieves a list of all banks from the database.
- **Provides static fetch method** – Allows getting all banks without creating an instance of _BankService_.
- **Creates a bank (basic)** – Adds a new bank with an ID, name, and passcode after checking if inputs are valid.
- **Creates a bank (custom settings)** – Adds a new bank with extra settings like deposit, withdrawal, and credit limits, as well as processing fees.

### **Deposit**
- an interface serves as a blueprint for deposit operations

### **FundTransfer**
- It is an  interface defines the contract for transferring funds between bank accounts. It provides two methods to facilitate both intra-bank and inter-bank transfers while ensuring proper validation and error handling.

### **LogService**
- The LogService class provides functionalities for managing transaction logs within the banking system. It interacts with an ITransactionDAO implementation to fetch transaction records for a specific account and log new transactions.

### **Payment**
- This interface defines a contact for processing payment between accounts in the banking system.


### **Recompense**
- This  interface defines a mechanism for repaying a loan or reducing an outstanding credit balance in a banking system.

### **ServiceProvider**
- This  class serves as a central access point for various banking services within the application. It initializes and provides singleton instances of key service classes, including BankService, AccountService, and LogService, ensuring that these services have a consistent and shared database provider through SQLiteDatabaseProvider.

### **Transaction**
- This class represents a record of a financial transaction in the system. Each transaction captures key details, including the account that initiated it, the type of transaction, a brief description, and a timestamp indicating when it occurred.
### **TransactionServices**
- This class handles various banking transactions, ensuring smooth and secure financial operations. It includes methods for transferring funds (both within the same bank and across different banks), depositing money, withdrawing funds, making credit payments, and repaying loans.
### **Withdrawal**
- This interface defines a simple contract for withdrawing money from an account using a specified method.
---

## **Documentation**  

### **Added Methods/Functions**  

### **Account.java**  
1. **loadTransactionsFromDatabase()**  
   - It clears the existing transaction list to avoid duplicates, fetches transactions from a log service using the account number, adds them back into the list, and prints the number of transactions loaded.
##### Why it was Added
   - To keep an account’s transaction history updated by automatically retrieving past transactions from a database. This amkes sure that it has accurate financial records without manual entry.

### **CreditAccount.java**  
1. **pay()**  
   -  allows a CreditAccount to send money to a SavingsAccount by checking if the credit limit allows the transaction. If the limit is exceeded, the payment fails. Otherwise, it processes the transfer through transactionService.creditPayment().
##### Why it was Added
   -  To allow credit accounts to make controlled payments while ensuring they do not exceed their borrowing limit.

2. **recompense()**  
   - It enables the CreditAccount to repay its loan balance by calling transactionService.recompense().
##### Why it was Added
   - To provide a way for users to settle their debts and restore available credit.

### **SavingsAccount.java**  
1. **transfer(account: Account, amount: double)**  
   -  allows a **SavingsAccount** to send money to another account within the same bank. It calls _transactionService.transferFunds()_ to process the transaction.
##### Why it was Added
   -  It was added so users can transfer funds between accounts without needing to withdraw cash manually.

2. **transfer(bank: Bank, account: Account, amount: double)**  
   -  enables a **SavingsAccount** to send money to an account in a different bank, applying a processing fee. It also calls _transactionService.transferFunds()_.
##### Why it was Added
   -  We added it to support interbank transfers, allowing users to send money outside their bank while ensuring feeas are applied.

3. **cashDeposit()**  
   -  allows a **SavingsAccount** to receive money by calling _transactionService.deposit()_.
##### Why it was Added
   -  So users can increase their account balance by depositing cash into their savings account.

4. **withdrawal()**
   - lets a **SavingsAccount** take out money by calling _transactionService.withdraw()_.
##### Why it was Added
   - was added so users can access their funds when needed, ensuring they can withdraw cash from their account.
5. **getAccountBalance()** 
   - returns the current balance of a **SavingsAccount**.
##### Why it was Added
   - was added to let users check how much money they have in their account.

### **Bank.java**  
1. **passcode** (Attribute)
   - Prevents unauthorized access to the bank’s data.
##### Why it was Added
   - is added to provide a security layer for the bank and to ensure only authorized users can access or modify bank details.

2. **withdrawLimit** (Attribute)
   - Helps regulate cash flow in the bank and it also ensures users to not withdraw excessive amounts that could lead to insufficient funds.
##### Why it was Added
   - is added to set a maximum amount a user can withdraw from their account at a time. To prevent large withdrawals that could cause financial instability.

3. **creditLimit** (Attribute)
   - it helps manage the bank’s risk by limiting excessive credit borrowing.
##### Why it was Added
   - was added to define the maximum credit (loan) a user can borrow. To prevent users from borrowing beyond what the bank can handle.


### **CreditAccountLauncher**
1. **creditAccountInit()**
   - This  method initializes the Credit Account menu after login. It ensures that a user is logged in before displaying the menu. 
   - The menu provides options to view loan statements, process credit payments, recompense credit balances, and view transaction history. It continuously runs until the user chooses to exit.
##### Why it was Added
   - It was added to provide structured way for users to interact with their credit accounts.
2. **creditPaymentProcess()**
   - This method handles the credit payment process.  It prompts the user to enter a recipient Savings Account number and a payment amount. It then validates the recipient account and processes the payment. 
   - If the transaction is successful, a confirmation message is displayed. Otherwise, an error message is shown for insufficient funds or an invalid recipient account.
##### Why it was Added
   - It was added to allow users to make credit payments while ensuring that funds are transferred only to valid Savings Account.
3. **creditRecompenseProcess()**
   - This  method manages the recompense process for a Credit Account. It prompts the user to enter a recompense amount and validates whether the entered amount does not exceed the outstanding loan balance.
   - If the recompense is successful, a confirmation message is displayed; otherwise, an error message is shown.
##### Why it was Added
   - This method was added to enable users to repay their credit balance and manage their debts efficiently.
4. **getLoggedAccount()**
   -  This method retrieves the currently logged-in Credit Account instance. It ensures that the logged-in account is a Credit Account by casting it from AccountLauncher.
##### Why it was Added
   - This method was added to provide a centralized way of accessing the logged-in credit account, preventing type mismatches and unauthorized operations.
### **SavingsAccountLauncher**
1.

### **BankLauncher**
1. **displayAllAccounts()**
   - It calls loggedBank.showAccounts(null), which displays every account in the bank, regardless of whether it is a credit, savings, student, or business account.
##### Why it was added
   - This method was added to provide a quick way to view all accounts under the currently logged-in bank without filtering by account type.

2. **displayAccounts(Class<? extends Account> accountType)**
   - It takes a class type as a parameter (CreditAccount.class, SavingsAccount.class, etc.) and calls loggedBank.showAccounts(accountType), displaying only accounts of the given type.
##### Why it was added
   - This method allows filtering accounts by type (Credit, Savings, etc.), making it easier to manage and view specific types of accounts.

3. **getBankByIndex(int index)**
   - It checks if the given index is valid and returns the corresponding bank wrapped in an Optional. If the index is out of range, it returns an empty Optional.
##### Why it was added
   - This method simplifies retrieving a bank using an index, which is useful for menus where banks are listed numerically.


---

📌 **Note:** More details will be added later.
