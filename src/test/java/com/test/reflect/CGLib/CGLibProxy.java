package com.test.reflect.CGLib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2016/12/7
 * @copyright: 2016 All rights reserved.
 */
public class CGLibProxy {

    /**
     *
     * @param obj 被委托代理的对象
     * @param callback 代理注入的逻辑
     * @param <T> 被委托代理对象的类型
     * @return
     */
    public static <T> T getInstance(final T obj, MethodInterceptor callback){
        Enhancer enhancer = new Enhancer();  //增强类
        //不同于JDK的动态代理。它不能在创建代理时传obj对 象，obj对象必须被CGLIB包来创建
        enhancer.setSuperclass(obj.getClass()); //设置被代理类字节码（obj将被代理类设置成父类；作为产生的代理的父类传进来的）。CGLIB依据字节码生成被代理类的子类
        enhancer.setCallback(callback);	//设置回调函数，即一个方法拦截
        return (T)enhancer.create(); //创建代理类
    }

    /**
     *
     * @param type 被委托代理的类型
     * @param callback 代理注入的逻辑
     * @param <T> 被委托代理对象的具体类型
     * @return
     */
    public static <T> T getInstance(final Class<T> type, MethodInterceptor callback){
        Enhancer enhancer = new Enhancer();  //增强类
        //不同于JDK的动态代理。它不能在创建代理时传obj对 象，obj对象必须被CGLIB包来创建
        enhancer.setSuperclass(type); //设置被代理类字节码
        enhancer.setCallback(callback);	//设置回调函数，即一个方法拦截
        return (T)enhancer.create(); //创建代理类
    }

}
