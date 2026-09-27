package com.banking.system.controller;

import com.banking.system.dto.ApiResponse;
import com.banking.system.dto.BalanceResponse;
import com.banking.system.dto.CreateAccountRequest;
import com.banking.system.dto.TransactionRequest;
import com.banking.system.model.BankAccount;
import com.banking.system.model.Transaction;
import com.banking.system.service.BankingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "*")
public class AccountController {

    private final BankingService bankingService;

    public AccountController(BankingService bankingService) {
        this.bankingService = bankingService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BankAccount>> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        try {
            BankAccount createdAccount = bankingService.createAccount(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Account created successfully", createdAccount));
        } catch (IllegalArgumentException e) {
            HttpStatus status = e.getMessage().contains("already exists") ? HttpStatus.CONFLICT : HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status)
                    .body(new ApiResponse<>(false, e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<BankAccount>> getAllAccounts() {
        return ResponseEntity.ok(bankingService.getAllAccounts());
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<ApiResponse<BankAccount>> getAccountDetails(@PathVariable int accountNumber) {
        try {
            BankAccount account = bankingService.getAccountDetails(accountNumber);
            return ResponseEntity.ok(new ApiResponse<>(true, "Account retrieved successfully", account));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage()));
        }
    }

    @PostMapping("/{accountNumber}/deposit")
    public ResponseEntity<ApiResponse<Void>> deposit(@PathVariable int accountNumber, @RequestBody TransactionRequest request) {
        try {
            double newBalance = bankingService.deposit(accountNumber, request.getAmount());
            return ResponseEntity.ok(new ApiResponse<>(true, "Deposit successful", newBalance));
        } catch (IllegalArgumentException e) {
            HttpStatus status = e.getMessage().equals("Account not found") ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status)
                    .body(new ApiResponse<>(false, e.getMessage()));
        }
    }

    @PostMapping("/{accountNumber}/withdraw")
    public ResponseEntity<ApiResponse<Void>> withdraw(@PathVariable int accountNumber, @RequestBody TransactionRequest request) {
        try {
            double newBalance = bankingService.withdraw(accountNumber, request.getAmount());
            return ResponseEntity.ok(new ApiResponse<>(true, "Withdrawal successful", newBalance));
        } catch (IllegalArgumentException e) {
            HttpStatus status = e.getMessage().equals("Account not found") ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status)
                    .body(new ApiResponse<>(false, e.getMessage()));
        }
    }

    @GetMapping("/{accountNumber}/balance")
    public ResponseEntity<ApiResponse<BalanceResponse>> getBalance(@PathVariable int accountNumber) {
        try {
            BankAccount account = bankingService.getAccountDetails(accountNumber);
            BalanceResponse balanceInfo = new BalanceResponse(
                    account.getAccountNumber(),
                    account.getAccountHolderName(),
                    account.getAccountType(),
                    account.getBalance()
            );
            return ResponseEntity.ok(new ApiResponse<>(true, "Balance retrieved successfully", balanceInfo));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage()));
        }
    }

    @GetMapping("/{accountNumber}/transactions")
    public ResponseEntity<ApiResponse<List<Transaction>>> getTransactionHistory(@PathVariable int accountNumber) {
        try {
            List<Transaction> transactions = bankingService.getTransactionHistory(accountNumber);
            return ResponseEntity.ok(new ApiResponse<>(true, "Transactions retrieved successfully", transactions));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, e.getMessage()));
        }
    }
}
