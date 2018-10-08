package com.klg.component;

import com.klg.component.interfaces.CacheLock;
import com.klg.component.interfaces.CacheParam;
import com.klg.component.interfaces.ICacheKeyGenerator;
import com.klg.utils.StringUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * Created by yaphiss on 2018/9/25.
 *
 * 通过接口注入的方式实现不同的生成规则
 */
public class LockKeyGenerator implements ICacheKeyGenerator {

    @Override
    public String getLockKey(ProceedingJoinPoint proceedingJoinPoint) {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        CacheLock lockAnnotation = method.getAnnotation(CacheLock.class);

        final Object[] args = proceedingJoinPoint.getArgs();
        final Parameter[] parameters = method.getParameters();
        StringBuilder builder = new StringBuilder();

        // 默认解析方法里带CacheParam注解的属性，若是没有，则尝试解析实体对象中的属性
        for (int i = 0; i < parameters.length; i++){
            final CacheParam annotation = parameters[i].getAnnotation(CacheParam.class);
            if(null == annotation) continue;

            builder.append(lockAnnotation.delimiter()).append(args[i]);
        }
        if (StringUtil.isEmpty(builder.toString())){
            final Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            for (int i = 0; i < parameterAnnotations.length; i++){
                final Object object = args[i];
                final Field[] fields = object.getClass().getDeclaredFields();
                for (Field field : fields){
                    final CacheParam annotation = field.getAnnotation(CacheParam.class);
                    if (null == annotation) continue;
                    field.setAccessible(true);
                    builder.append(lockAnnotation.delimiter()).append(ReflectionUtils.getField(field, object));
                }
            }
        }

        return lockAnnotation.prefix() + builder.toString();
    }
}
