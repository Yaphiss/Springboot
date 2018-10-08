package com.klg.component.interfaces;

import java.lang.annotation.*;

/**
 * Created by yaphiss on 2018/9/25.
 */

@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CacheParam {

    /**
     * 字段名称
     * @return
     */
    String name() default "";
}
