package com.test.reflect.CGLib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2016/12/7
 * @copyright: 2016 All rights reserved.
 */
public class MyInterceptor implements MethodInterceptor {

    static final private Logger logger = LoggerFactory.getLogger(MyInterceptor.class);

    @Override
    public Object intercept(Object obj, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        logger.info("do something before method {}", method.getName());
        logger.info("obj: {}", obj.getClass().getName());  //由CGLib动态生成的代理类的class name
        logger.info("method: {}", methodProxy.getSignature().getName()); //被代理对象中此次调用的方法名称
        logger.info("methodProxy: {}", methodProxy.getSuperName()); //代理对象中此次调用的方面名称

        Object object = methodProxy.invokeSuper(obj, objects);  //调用代理类实例上的methodProxy方法的父类方法
        logger.info("do something after method {}", method.getName());
        return object;
    }
}
