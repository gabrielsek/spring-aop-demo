package org.example.aspect;

import org.springframework.stereotype.Service;

@Service
public class BankingService {

    @AuditLog(action = "MONEY_TRANSFER")
    public void transfer(String from, String to, double amount) {
        if (amount > 10000) {
            throw new SecurityException("Large transfers require manual approval!");
        }
        System.out.println("Transferring $" + amount + " from " + from + " to " + to);
    }

    public double getBalance(String accountId) {
        // Simulating a flaky database connection
        if (Math.random() > 0.5) throw new RuntimeException("Database Timeout!");
        return 500.0;
    }
}