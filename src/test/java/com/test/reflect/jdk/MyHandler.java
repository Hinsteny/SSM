package com.test.reflect.jdk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2016/12/2
 * @copyright: 2016 All rights reserved.
 */
public class MyHandler implements InvocationHandler {

    static final private Logger logger = LoggerFactory.getLogger(MyHandler.class);

    private Object object = null;

    public MyHandler(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.info("do something before method {}", method.getName());
        Object ret = method.invoke(this.object, args);
        logger.info("do something after method {}", method.getName());

        return ret;
    }
}
