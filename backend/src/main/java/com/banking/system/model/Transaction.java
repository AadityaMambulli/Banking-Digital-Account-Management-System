package com.banking.system.model;

import java.time.LocalDateTime;

public class Transaction {
    private String transactionId;
    private int accountNumber;
    private TransactionType transactionType;
    private double amount;
    private double resultingBalance;
    private LocalDateTime timestamp;

    public Transaction() {
    }

    public Transaction(String transactionId, int accountNumber, TransactionType transactionType, double amount, double resultingBalance, LocalDateTime timestamp) {
        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.amount = amount;
        this.resultingBalance = resultingBalance;
        this.timestamp = timestamp;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getResultingBalance() {
        return resultingBalance;
    }

    public void setResultingBalance(double resultingBalance) {
        this.resultingBalance = resultingBalance;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
