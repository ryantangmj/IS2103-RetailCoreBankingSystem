# Retail Core Banking System

**Merlion Bank** is a new retail bank that will be opening in Singapore soon. This repository contains the source code and project structure for the Retail Core Banking System (RCBS) being developed for Merlion Bank. The RCBS is a critical component of the bank, consisting of a core backend with a component-based architecture and multiple retail banking applications to support the business operation of the bank. The development of the RCBS will be carried out in multiple phases over a period of 9 weeks.

## Project Structure

In the first phase of development, the focus is on creating the basic NetBeans project structure for the RCBS, along with two enterprise application clients representing the Teller Terminal application and the Automated Teller Machine (ATM) application. The NetBeans enterprise application project structure should resemble the following:

- **RetailCoreBankingSystem**
  - **RetailCoreBankingSystem-ejb**
  - **RetailCoreBankingSystemLibrary**
  - **TellerTerminalClient**
  - **AutomatedTellerMachineClient**

## Use Case Descriptions and Business Rules

During the development of the RCBS, several use cases and business rules need to be implemented. Here's an overview of the key use cases and their associated business rules:

### 1. Create Customer

- For a new customer, the teller needs to create a new customer record before performing any other business activities.
- Each customer should be uniquely identified with one customer record.

### 2. Open Deposit Account

- The teller opens a new deposit account for an existing customer.
- The customer needs to provide an initial cash deposit of any amount.
- A customer can have multiple deposit accounts of different types.
- For this phase, only savings accounts are required.
- For this phase, you may assume that there is only an individual account (i.e., a customer cannot open a joint account).

### 3. Issue ATM Card

- The teller issues a new ATM card to the customer.
- An ATM card may be associated with one or more deposit accounts.
- Each deposit account may be associated with zero or one ATM card.

### 3.1 Issue Replacement ATM Card

- The teller issues a replacement ATM card to the customer.
- Delete the corresponding record of the previous ATM card.
- Create a new ATM card record as the replacement.

### 4. Insert ATM Card

- The customer enters the ATM card number and PIN.

### 5. Change PIN

- The customer changes the current PIN of the ATM card.

### 6. Enquire Available Balance

- The customer enquires about the available balance for a deposit account associated with the ATM card.
- Available balance refers to the balance amount in a deposit account that is available for spending, withdrawal, or transfers.
- Ledger balance is equal to the sum of available balance plus any holding balance.
- If there is no holding balance, then the ledger balance is equal to the available balance.


