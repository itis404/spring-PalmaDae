package org.palmadae.donortrack.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Pointcut("execution(public * org.palmadae.donortrack.service.*.*(..))")
    public void serviceLayer() {}

    @Pointcut("execution(public * org.palmadae.donortrack.repository.*.*(..))")
    public void repositoryLayer() {}

    @Around("serviceLayer() || repositoryLayer()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String method = joinPoint.getSignature().toShortString();

        Object[] args = joinPoint.getArgs();

        log.info("Starting {} with argd = {}", method, Arrays.toString(args));

        long start = System.nanoTime();

        try {
            Object result = joinPoint.proceed();
            long time = System.nanoTime();
            log.info("Finished {} time = {} result = {}", method, time - start, result);
            return result;
        } catch (Throwable ex) {
            log.error("Failed {} reason = {}", method, ex.getMessage());
            throw ex;
        }
    }
}
