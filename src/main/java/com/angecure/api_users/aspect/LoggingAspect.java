package com.angecure.api_users.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.angecure.api_users.controller..*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object[] args = joinPoint.getArgs();
        StringBuilder inputParams = new StringBuilder();
        for (Object arg : args) {
            inputParams.append(arg).append(" ");
        }
        logger.info("Entering method: " + joinPoint.getSignature() + " with arguments: " + inputParams.toString().trim());

        Object result = joinPoint.proceed();

        logger.info("Exiting method: " + joinPoint.getSignature() + " with result: " + result.toString());

        long executionTime = System.currentTimeMillis() - start;
        logger.info("Method " + joinPoint.getSignature() + " executed in " + executionTime + "ms");

        return result;
    }
}
