package com.banking.system;

import com.banking.system.dto.CreateAccountRequest;
import com.banking.system.model.AccountType;
import com.banking.system.model.BankAccount;
import com.banking.system.model.Transaction;
import com.banking.system.model.TransactionType;
import com.banking.system.repository.InMemoryBankRepository;
import com.banking.system.service.BankingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BankingServiceTest {

    private BankingService bankingService;
    private InMemoryBankRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryBankRepository();
        bankingService = new BankingService(repository);
    }

    @Test
    void testCreateAccountSuccess() {
        CreateAccountRequest req = new CreateAccountRequest();
        req.setAccountNumber(101);
        req.setAccountHolderName("Aaditya");
        req.setEmail("aaditya@example.com");
        req.setPhoneNumber("9876543210");
        req.setAccountType(AccountType.SAVINGS);
        req.setInitialDeposit(10000.0);

        BankAccount account = bankingService.createAccount(req);

        assertNotNull(account);
        assertEquals(101, account.getAccountNumber());
        assertEquals("Aaditya", account.getAccountHolderName());
        assertEquals(10000.0, account.getBalance());
        assertEquals(1, account.getTransactionHistory().size());
        assertEquals(TransactionType.DEPOSIT, account.getTransactionHistory().get(0).getTransactionType());
    }

    @Test
    void testCreateDuplicateAccountThrowsException() {
        CreateAccountRequest req = new CreateAccountRequest();
        req.setAccountNumber(101);
        req.setAccountHolderName("Aaditya");
        req.setAccountType(AccountType.SAVINGS);
        req.setInitialDeposit(1000.0);

        bankingService.createAccount(req);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            bankingService.createAccount(req);
        });
        assertEquals("Account number already exists", ex.getMessage());
    }

    @Test
    void testDepositSuccess() {
        CreateAccountRequest req = new CreateAccountRequest();
        req.setAccountNumber(102);
        req.setAccountHolderName("Rahul");
        req.setAccountType(AccountType.CURRENT);
        req.setInitialDeposit(5000.0);
        bankingService.createAccount(req);

        double newBalance = bankingService.deposit(102, 2000.0);

        assertEquals(7000.0, newBalance);
        BankAccount account = bankingService.getAccountDetails(102);
        assertEquals(7000.0, account.getBalance());
        assertEquals(2, account.getTransactionHistory().size());
    }

    @Test
    void testWithdrawalSuccess() {
        CreateAccountRequest req = new CreateAccountRequest();
        req.setAccountNumber(103);
        req.setAccountHolderName("Priya");
        req.setAccountType(AccountType.SAVINGS);
        req.setInitialDeposit(5000.0);
        bankingService.createAccount(req);

        double newBalance = bankingService.withdraw(103, 3000.0);

        assertEquals(2000.0, newBalance);
        BankAccount account = bankingService.getAccountDetails(103);
        assertEquals(2000.0, account.getBalance());
        assertEquals(2, account.getTransactionHistory().size());
        assertEquals(TransactionType.WITHDRAWAL, account.getTransactionHistory().get(1).getTransactionType());
    }

    @Test
    void testInsufficientBalanceWithdrawalThrowsException() {
        CreateAccountRequest req = new CreateAccountRequest();
        req.setAccountNumber(104);
        req.setAccountHolderName("Sneha");
        req.setAccountType(AccountType.SAVINGS);
        req.setInitialDeposit(1000.0);
        bankingService.createAccount(req);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            bankingService.withdraw(104, 2000.0);
        });
        assertEquals("Insufficient balance", ex.getMessage());
    }

    @Test
    void testAccountNotFoundThrowsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            bankingService.getAccountDetails(999);
        });
        assertEquals("Account not found", ex.getMessage());
    }

    @Test
    void testTransactionCreationVerification() {
        CreateAccountRequest req = new CreateAccountRequest();
        req.setAccountNumber(105);
        req.setAccountHolderName("Amit");
        req.setAccountType(AccountType.SAVINGS);
        req.setInitialDeposit(500.0);
        bankingService.createAccount(req);

        bankingService.deposit(105, 1500.0);
        bankingService.withdraw(105, 700.0);

        List<Transaction> history = bankingService.getTransactionHistory(105);
        assertEquals(3, history.size());
        assertEquals(1300.0, history.get(2).getResultingBalance());
    }
}
