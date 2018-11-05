package com.newcoder.question.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//@Aspect
//@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
    @Before("execution(* com.newcoder.question.Controller.*.*(..))")
    public void before(JoinPoint point){
        /*StringBuilder sb= new StringBuilder();
        for(Object obj : point.getArgs()){
            sb.append(obj.toString()+" ");
        }*/
        logger.info("before method");
        //logger.info(sb.toString());
    }
    @After("execution(* com.newcoder.question.Controller.*.*(..))")
    public void after(){
        logger.info("after method");
    }

}
