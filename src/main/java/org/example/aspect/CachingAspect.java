package org.example.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class CachingAspect {

    private final Map<String, Object> cache = new ConcurrentHashMap<>();

    @Around("@annotation(Cacheable)")
    public Object cache(ProceedingJoinPoint joinPoint) throws Throwable {
        String cacheKey = generateKey(joinPoint);
        if (cache.containsKey(cacheKey)) {
            System.out.println("[Cache] Hit for key: " + cacheKey);
            return cache.get(cacheKey);
        }

        System.out.println("[Cache] Miss for key: " + cacheKey);
        Object result = joinPoint.proceed();
        cache.put(cacheKey, result);

        return result;
    }

    private String generateKey(ProceedingJoinPoint joinPoint) {
        return joinPoint.getSignature().toShortString() + Arrays.toString(joinPoint.getArgs());
    }
}
