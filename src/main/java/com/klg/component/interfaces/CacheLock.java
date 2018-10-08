package com.klg.component.interfaces;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by yaphiss on 2018/9/25.
 *
 * 资源锁，基于REDIS Cache实现分布式锁
 *
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CacheLock {

    /**
     * redis锁key的前缀
     * @return 锁key前缀
     */
    String prefix() default "";

    /**
     * 过期时长，单位：秒
     * @return 轮询时间
     */
    int expire() default 60;

    /**
     * 超时时间单位
     * @return 秒
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * key的分隔符，默认为：":"
     * @return
     */
    String delimiter() default ":";

}
