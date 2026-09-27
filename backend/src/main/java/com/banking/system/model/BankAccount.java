package com.banking.system.model;

import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    private int accountNumber;
    private String accountHolderName;
    private String email;
    private String phoneNumber;
    private AccountType accountType;
    private double balance;
    private List<Transaction> transactionHistory;

    public BankAccount() {
        this.transactionHistory = new ArrayList<>();
    }

    public BankAccount(int accountNumber, String accountHolderName, String email, String phoneNumber, AccountType accountType, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public void setTransactionHistory(List<Transaction> transactionHistory) {
        this.transactionHistory = transactionHistory;
    }

    public void addTransaction(Transaction transaction) {
        if (this.transactionHistory == null) {
            this.transactionHistory = new ArrayList<>();
        }
        this.transactionHistory.add(transaction);
    }
}
