package com.banking.system.service;

import com.banking.system.dto.CreateAccountRequest;
import com.banking.system.model.BankAccount;
import com.banking.system.model.Transaction;
import com.banking.system.model.TransactionType;
import com.banking.system.repository.InMemoryBankRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class BankingService {

    private final InMemoryBankRepository repository;

    public BankingService(InMemoryBankRepository repository) {
        this.repository = repository;
    }

    public BankAccount createAccount(CreateAccountRequest request) {
        if (request.getAccountNumber() == null) {
            throw new IllegalArgumentException("Account number is required");
        }
        if (repository.existsByAccountNumber(request.getAccountNumber())) {
            throw new IllegalArgumentException("Account number already exists");
        }
        if (request.getAccountHolderName() == null || request.getAccountHolderName().trim().isEmpty()) {
            throw new IllegalArgumentException("Account holder name cannot be empty");
        }
        if (request.getInitialDeposit() < 0) {
            throw new IllegalArgumentException("Initial deposit cannot be negative");
        }

        BankAccount account = new BankAccount(
                request.getAccountNumber(),
                request.getAccountHolderName().trim(),
                request.getEmail(),
                request.getPhoneNumber(),
                request.getAccountType(),
                request.getInitialDeposit()
        );

        if (request.getInitialDeposit() > 0) {
            Transaction initialTx = new Transaction(
                    UUID.randomUUID().toString().substring(0, 8),
                    account.getAccountNumber(),
                    TransactionType.DEPOSIT,
                    request.getInitialDeposit(),
                    account.getBalance(),
                    LocalDateTime.now()
            );
            account.addTransaction(initialTx);
        }

        return repository.save(account);
    }

    public BankAccount getAccountDetails(int accountNumber) {
        return repository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
    }

    public List<BankAccount> getAllAccounts() {
        return repository.findAll();
    }

    public double deposit(int accountNumber, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than zero");
        }
        BankAccount account = getAccountDetails(accountNumber);
        double newBalance = account.getBalance() + amount;
        account.setBalance(newBalance);

        Transaction transaction = new Transaction(
                UUID.randomUUID().toString().substring(0, 8),
                accountNumber,
                TransactionType.DEPOSIT,
                amount,
                newBalance,
                LocalDateTime.now()
        );
        account.addTransaction(transaction);
        repository.save(account);

        return newBalance;
    }

    public double withdraw(int accountNumber, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero");
        }
        BankAccount account = getAccountDetails(accountNumber);
        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        double newBalance = account.getBalance() - amount;
        account.setBalance(newBalance);

        Transaction transaction = new Transaction(
                UUID.randomUUID().toString().substring(0, 8),
                accountNumber,
                TransactionType.WITHDRAWAL,
                amount,
                newBalance,
                LocalDateTime.now()
        );
        account.addTransaction(transaction);
        repository.save(account);

        return newBalance;
    }

    public List<Transaction> getTransactionHistory(int accountNumber) {
        BankAccount account = getAccountDetails(accountNumber);
        return account.getTransactionHistory();
    }
}
