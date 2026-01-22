package org.example.aspect;

import org.springframework.stereotype.Service;

@Service
public class BankingService {

    @AuditLog(action = "MONEY_TRANSFER")
    @LogExecutionTime
    @Validate
    public void transfer(String from, String to, double amount) {
        if (amount > 10000) {
            throw new SecurityException("Large transfers require manual approval!");
        }
        try {
            // Simulate some work
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Transferring $" + amount + " from " + from + " to " + to);
    }

    public double getBalance(String accountId) {
        // Simulating a flaky database connection
        if (Math.random() > 0.5) throw new RuntimeException("Database Timeout!");
        return 500.0;
    }

    @Cacheable
    public String getAccountDetails(String accountId) {
        try {
            // Simulate a slow remote call
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Account Details for " + accountId + ": [Balance: $999.99, Owner: Some User]";
    }
}