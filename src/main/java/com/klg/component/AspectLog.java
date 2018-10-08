package com.klg.component;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by yaphiss on 2018/9/20.
 *
 * AOP切入日志类，后续将使用在controller层面
 */
@Aspect
@Component
public class AspectLog {

    private static Logger logger = Logger.getLogger(AspectLog.class);

    /**
     * HttpClientUtil访问方法之前 切入
     * 1.com.klg.api.*.*
     * 这个路径 是一直要指定到你的方法层级。
     * 如：com.klg.api.java
     * 需要配置的execution就是  com.klg.api.*.*(..)
     * 第一个*指的是controller 下的所有类
     * 第二个*指的是类里面的所有方法
     * 2.如果这块指定的目录没有到方法，则会抛出异常。
     * @param join
     */
    @Before("execution(public * com.klg.api.*.*(..))")
    public void beforeLog(JoinPoint join){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        logger.info("URL: "+request.getRequestURL());
        logger.info("REQUEST METHOD: "+ request.getMethod());
        //打印类方法
        logger.info("CLASS METHOD: " + join.getStaticPart().getSignature().getDeclaringTypeName()+"."+join.getSignature().getName());
        logger.info("REQUEST PARAMS: "+ join.getArgs());
        logger.info("REQUEST IP: "+request.getLocalAddr());
    }

    @AfterReturning(returning = "object", pointcut = "execution(public * com.klg.api.*.*(..))")
    public void afterRequestLog(Object object){
        logger.info("REQUEST RESULT: "+object.toString());
    }

}
