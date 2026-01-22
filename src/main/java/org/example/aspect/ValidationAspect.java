package org.example.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class ValidationAspect {

    @Before("@annotation(Validate)")
    public void validateArguments(JoinPoint joinPoint) {
        System.out.println("[Validation] Checking arguments for: " + joinPoint.getSignature().getName());
        Arrays.stream(joinPoint.getArgs()).forEach(arg -> {
            if (arg == null) {
                throw new IllegalArgumentException("Method argument cannot be null.");
            }
        });
    }
}
