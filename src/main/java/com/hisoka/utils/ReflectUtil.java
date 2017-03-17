package com.hisoka.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Hinsteny
 * @Describtion 反射相关的工具
 * @date 2017/3/16
 * @copyright: 2016 All rights reserved.
 */
public class ReflectUtil {

    /**
     * 把一个对象中的所有属性转化放入到一个Map中[FieldName:FieldValue]
     * @param object
     * @param parent 是否转化父类中的属性
     * @return
     */
    public static Map<String, String> objectToMap(Object object, boolean parent){
        Map<String, String> result = new HashMap<String, String>();
        if(object != null){
            Class<? extends Object> present = object.getClass();
            getProFromClass(present, result, object);
            if(parent)
                return getClassPro(present, result, object);
        }

        return result;
    }

    /**
     * 递归获取一个对象[object]中所包含类[class]及其所有父类里面的全部属性值放入到map中
     * @param present
     * @param result
     * @param object
     * @return
     */
    private static Map<String, String> getClassPro(Class<? extends Object> present,
                                                   Map<String, String> result, Object object){
        if(!present.equals(Object.class)){
            // 鉴于Object类本身没有任何属性, 因此把从类上获取属性值的操作至于此
            getProFromClass(present, result, object);
            present = present.getSuperclass();
            return getClassPro(present, result, object);
        }
        return result;
    }

    /**
     * 从一个对象[object]获取指定类[class]里面的全部属性值放入到map中
     * @param present
     * @param result
     * @param object
     */
    private static void getProFromClass(Class<? extends Object> present,
                                        Map<String, String> result, Object object){
        Field[] declaredFields = present.getDeclaredFields();
        if(declaredFields.length > 0){
            for(int i=0,j=declaredFields.length; i < j; i++){
                Field field = declaredFields[i];
                String fieldName = field.getName();
                result.put(fieldName, getFieldValue(object, field));
            }
        }

    }

    //这里可以在属性[Field]上加注解自定义对象上值的获取
    private static String getFieldValue(Object object, Field field){
        Class<?> type = field.getType();
        field.setAccessible(true);
        Object value= null;
        try {
            value = field.get(object);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        String fieldV = "";
        if(value != null){
            if(char.class == type || Character.class == type){
                fieldV = String.valueOf(value);
            } else if(int.class == type || Integer.class == type){
                fieldV = String.valueOf(value);
            } else if(float.class == type || Float.class == type){
                fieldV = String.valueOf(value);
            } else if(double.class == type || Double.class == type){
                fieldV = String.valueOf(value);
            } else {
                // 没法了 tostring 吧
                fieldV = value.toString();
            }
        }
        return fieldV;
    }

    public static void main(String[] args){

    }
}
