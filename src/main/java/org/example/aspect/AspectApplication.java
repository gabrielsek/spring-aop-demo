package org.example.aspect;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AspectApplication {

    public static void main(String[] args) {
        SpringApplication.run(AspectApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(BankingService bankingService) {
        return args -> {
            System.out.println("--- Scenario 1: Successful Transfer ---");
            try {
                bankingService.transfer("Alice", "Bob", 250.0);
            } catch (Exception e) {
                // Handled by Aspect
            }

            System.out.println("\n--- Scenario 2: Security Violation (Amount > 10,000) ---");
            try {
                bankingService.transfer("Alice", "Vault", 50000.0);
            } catch (Exception e) {
                // The Aspect @AfterThrowing will log this
            }

            System.out.println("\n--- Scenario 3: Flaky Database (Retry Logic) ---");
            try {
                double balance = bankingService.getBalance("ACC-123");
                System.out.println("Final Result: Balance is $" + balance);
            } catch (Exception e) {
                System.out.println("Final Result: Database failed after retries.");
            }
        };
    }
}