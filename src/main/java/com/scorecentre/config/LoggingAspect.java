package com.scorecentre.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.stereotype.Component;



@Aspect
@Component

/** AOP Logger for FootballDataService and FootballDataController 
 * Runs on each method call, so calls are logged
 * 
 */
public class LoggingAspect {

    private static final Logger logger = LogManager.getLogger(LoggingAspect.class);

    @Before("execution(* com.scorecentre.footballData.FootballDataService.*(..)) || " +
            "execution(* com.scorecentre.footballData.FootballDataController.*(..))")
    public void logMethodCall(JoinPoint joinPoint) {
        logger.info("Called: {}.{} with args: {}",
            joinPoint.getTarget().getClass().getSimpleName(),
            joinPoint.getSignature().getName(),
            joinPoint.getArgs());
    }
}

