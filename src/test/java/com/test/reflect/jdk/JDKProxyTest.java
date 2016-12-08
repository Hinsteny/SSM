package com.test.reflect.jdk;

import com.test.reflect.Scholar;
import com.test.reflect.ScholarImpl;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2016/12/2
 * @copyright: 2016 All rights reserved.
 */
public class JDKProxyTest {

    static final private Logger logger = LoggerFactory.getLogger(JDKProxyTest.class);

    @Test
    public void testProxy(){
        // 元对象(被代理对象)
        ScholarImpl managerImpl = new ScholarImpl();

        // 业务代理类
        MyHandler securityHandler = new MyHandler(managerImpl);

        // 获得代理类($Proxy0 extends Proxy implements Manager)的实例.
        Scholar managerProxy = (Scholar) Proxy.newProxyInstance(JDKProxyTest.class.getClassLoader(), managerImpl.getClass().getInterfaces(), securityHandler);

        logger.info("Scholar local is {}", managerProxy.LOCAL);
        logger.info("Scholar default method do and result is {}", managerProxy.infoLocal());
        String theme = "knowledge";
        String result = managerProxy.searching(theme);
        logger.info("result is {}", result);
    }

    @Test
    public void testGetProxyClass_1(){
        // 需要添加java命令执行时参数 [-Dsun.misc.ProxyGenerator.saveGeneratedFiles=true]
        // 然后生成的动态代理对象会在下面目录的子目录 [com\sun\proxy] 中, 命名是从 [$Proxy#.class](#从0开始依次加1递增)
        System.out.println(System.getProperty("user.dir"));
        testProxy();
        // 可以去对应目录去查看生成的动态class类文件, 反编译出动态类的java代码
        logger.info("Finish at here {}");
    }

    @Test
    public void testGetProxyClass_2(){
        // 获取代理类的字节码
        byte[] classFile = ProxyGenerator.generateProxyClass("$Proxy11", ScholarImpl.class.getInterfaces());
        // 写入文件
        String path = "D://data/$Proxy1.class";
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(path);
            out.write(classFile);
            out.flush();
        } catch (Exception e) {
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

}