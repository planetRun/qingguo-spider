package org.choviwu.top.qg.util;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ClassUtils {

    /**
     * 继承 反射获取字段
     * @param bean
     * @param <T>
     * @return
     */
    public static <T> List<Field> getClassFields(T bean){
        return sortField(bean.getClass(),new ArrayList<>());
    }
    /**
     * 递归获取字段
     * @param tClass 轮询到的当前类
     * @param list   字段list
     * @return  返回字段的List
     */
    private static <T> List<Field> sortField(Class<T> tClass,List<Field> list){
        if(StringUtils.isEmpty(tClass)){
            return list;
        }
        Field[] fields = tClass.getDeclaredFields();
        for (Field field : fields){
            list.add(field);
        }
        Class clazz = tClass.getSuperclass();
        if(clazz==null){
            return list;
        }
        return sortField(clazz.getSuperclass(),list);
    }
}
