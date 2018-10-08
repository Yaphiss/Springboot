package com.klg.component.interfaces;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * redis key generator
 */
public interface ICacheKeyGenerator {

    /**
     * 获取AOP参数，生成指定缓存key
     * @param proceedingJoinPoint
     * @return 缓存KEY
     */
    String getLockKey(ProceedingJoinPoint proceedingJoinPoint);
}