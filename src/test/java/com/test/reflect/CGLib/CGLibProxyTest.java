package com.test.reflect.CGLib;

import com.test.reflect.ScholarImpl;
import net.sf.cglib.core.DefaultGeneratorStrategy;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2016/12/2
 * @copyright: 2016 All rights reserved.
 */
public class CGLibProxyTest {

    static final private Logger logger = LoggerFactory.getLogger(CGLibProxyTest.class);

    /**
     * 根据一个java class对象生成其自身的代理对象, 并测试执行它相关的方法
     */
    @Test
    public void testProxy_1(){
        // 代理工厂
        CGLibProxy proxy = new CGLibProxy();
        // 获取生成的动态代理对象
        ScholarImpl scholar = proxy.getInstance(ScholarImpl.class, new MyInterceptor());

        logger.info("Scholar local is {}", scholar.LOCAL);
        logger.info("Scholar default method do and result is {}", scholar.infoLocal());
        logger.info("ScholarImpl printTime method do {}", scholar.printTime());
        String theme = "knowledge";
        String result = scholar.searching(theme);
        logger.info("result is {}", result);
    }

    /**
     * 根据一个java 实例对象生成其自身的代理对象, 并测试执行它相关的方法
     */
    @Test
    public void testProxy_2(){
        // 元对象(被代理对象)
        ScholarImpl managerImpl = new ScholarImpl();
        // 代理工厂
        CGLibProxy proxy = new CGLibProxy();
        // 获取生成的动态代理对象
        ScholarImpl scholar = proxy.getInstance(managerImpl, new MyInterceptor());

        logger.info("Scholar local is {}", scholar.LOCAL);
        logger.info("Scholar default method do and result is {}", scholar.infoLocal());
        logger.info("ScholarImpl printTime method do {}", scholar.printTime());
        String theme = "knowledge";
        String result = scholar.searching(theme);
        logger.info("result is {}", result);
    }

    /**
     * 获取动态生成代理对象字节码文件, 反编译查看源码
     */
    @Test
    public void testGetProxyClass_1(){
        // 代理工厂
        CGLibProxy proxy = new CGLibProxy();
        String filePath = "D://data/CGLib$ProxyScholarImpl.class";
        OutputStream out = null;
        try {
            out = new FileOutputStream(filePath);
            // 获取生成的动态代理对象
            ScholarImpl scholar = getProxyInstance(ScholarImpl.class, new MyInterceptor(), out);
            logger.info("Class file is local in {}", filePath);
            scholar.printTime();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private  <T> T getProxyInstance(final Class<T> type, MethodInterceptor callback, OutputStream out){
        Enhancer enhancer = new Enhancer();  //增强类
        enhancer.setSuperclass(type); //设置被代理类字节码
        enhancer.setStrategy(new DefaultGeneratorStrategy() {
            protected byte[] transform(byte[] b) {
                // do something with bytes here
                try {
                    out.write(b);
                    out.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return b;
            }});
        enhancer.setCallback(callback);	//设置回调函数，即一个方法拦截
        return (T)enhancer.create(); //创建代理类
    }
}