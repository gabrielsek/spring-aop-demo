package org.example.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SecurityAndAuditAspect {

    // Matches the transfer method in your specific package
    @Before("execution(* org.example.aspect.BankingService.transfer(..)) && args(from, to, amount)")
    public void checkSecurity(String from, String to, double amount) {
        System.out.println("[Security] Validating transfer for: $" + amount);
    }

    // Matches any method annotated with @AuditLog in your project
    @AfterReturning(pointcut = "@annotation(auditAnnotation)", returning = "result")
    public void logAction(AuditLog auditAnnotation, Object result) {
        System.out.println("[Audit] Action logged: " + auditAnnotation.action());
    }

    // Matches any method in your specific package that throws an exception
    @AfterThrowing(pointcut = "execution(* org.example.aspect.*.*(..))", throwing = "ex")
    public void logError(Exception ex) {
        System.err.println("[Alert] Exception caught by Aspect: " + ex.getMessage());
    }

    // Around advice for the retry logic
    @Around("execution(* org.example.aspect.BankingService.getBalance(..))")
    public Object retryDatabase(ProceedingJoinPoint joinPoint) throws Throwable {
        int attempts = 0;
        while (attempts < 3) {
            try {
                return joinPoint.proceed();
            } catch (RuntimeException e) {
                attempts++;
                System.out.println("[Retry] Attempt " + attempts + " failed...");
                if (attempts >= 3) throw e;
            }
        }
        return null;
    }
}
