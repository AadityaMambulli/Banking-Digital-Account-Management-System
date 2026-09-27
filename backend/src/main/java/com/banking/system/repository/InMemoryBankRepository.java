package com.banking.system.repository;

import com.banking.system.model.BankAccount;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryBankRepository {
    private final Map<Integer, BankAccount> accounts = new ConcurrentHashMap<>();

    public BankAccount save(BankAccount account) {
        accounts.put(account.getAccountNumber(), account);
        return account;
    }

    public Optional<BankAccount> findByAccountNumber(int accountNumber) {
        return Optional.ofNullable(accounts.get(accountNumber));
    }

    public boolean existsByAccountNumber(int accountNumber) {
        return accounts.containsKey(accountNumber);
    }

    public List<BankAccount> findAll() {
        return new ArrayList<>(accounts.values());
    }
}
