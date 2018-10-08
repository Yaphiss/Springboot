package com.klg.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by yaphiss on 2018/9/19.
 */
public class MapUtil {

    public static boolean isExist (Map map){
        return null!= map && !map.isEmpty();
    }

    /**
     * map转换成object
     * @param map 需要转换的map
     * @param object 需要转换的对象
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T transfer(Map<String, Object> map, Object object) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

        for (PropertyDescriptor property : propertyDescriptors){
            String key = property.getName();
            if(map.containsKey(key)){
                Object value = map.get(key);
                // 得到property对应的setter方法
                Method setter = property.getWriteMethod();
                setter.invoke(object, value);
            }
        }
        return (T) object;
    }
}
