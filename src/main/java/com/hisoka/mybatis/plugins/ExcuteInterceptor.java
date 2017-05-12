package com.hisoka.mybatis.plugins;

import org.apache.ibatis.executor.BaseExecutor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Properties;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2017/4/10
 * @copyright: 2016 All rights reserved.
 */
@Intercepts(@Signature(type= StatementHandler.class,method = "update", args = {MappedStatement.class, Object.class}))
public class ExcuteInterceptor implements Interceptor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        HashSet<BaseExecutor> x = new HashSet<>();
        logger.info("target: {}, method: {}", invocation.getTarget(), invocation.getMethod());
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        logger.info("target class: {}", target.getClass());
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        logger.info("Start println properties ...");
        properties.values().stream().forEach((item) -> System.out.println(item));
        logger.info("Finished println properties!");
    }
}
