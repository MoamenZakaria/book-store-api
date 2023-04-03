package com.moamen.store.aop;


import com.moamen.store.constant.Defines;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;


@Component
@Aspect
@Slf4j
public class RepositoryLoggingAspect {

    @Around(Defines.AspectPointCuts.REPOSITORY)
    public Object serviceAroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Object obj = null;
        String methodName = signature.getMethod().getName();
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName() ;
        long startTime = System.currentTimeMillis();
        log.info("Repository::" + className + "." + methodName + " Called");
        try {
            obj = joinPoint.proceed();
        } finally {
            log.info("Repository::" + className + "." + methodName + " Finished in [" + (System.currentTimeMillis() - startTime) + "] ms");
        }
        return obj;
    }

    @AfterThrowing(pointcut = Defines.AspectPointCuts.SERVICES, throwing = "error")
    public void serviceAfterThrowingAdvice(JoinPoint joinPoint, Throwable error) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName() ;
        String errorID = String.valueOf(System.currentTimeMillis());
        log.info("Repository::" + className + "." + methodName + " Throws Error, Error ID : " + errorID);
        log.error("Repository::" + className + "." + methodName + " Throws Error With ID (" + errorID + ")-> ", error);
    }

}
