package com.example.board.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class LoggerAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around("execution(* com.example.board..controller.*Controller.*(..)) or execution(* com.example.board..service.*Impl.*(..)) or execution(* com.example.board..mapper.*Mapper.*(..))")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable{
        /*
        printLog의 파라미터인 ProceedingJoinPoint 인터페이스가 상속받는 클래스는 다음 메서드를 포함합니다.
        getSignature은 실행되는 대상 객체의 메서드에 대한 정보를 가지고 옵니다.
        */

        String type = "";
        String name = joinPoint.getSignature().getDeclaringTypeName();

        if(name.contains("Controller") == true){
            type = "Controller ===>";
        }
        else if(name.contains("Service") == true){
            type = "Service ===>";
        }
        else if(name.contains("Mapper") == true){
            type = "Mapper ===>";
        }

        logger.debug(type + name + "." + joinPoint.getSignature().getName() + "()");
        return joinPoint.proceed();
    }
}
