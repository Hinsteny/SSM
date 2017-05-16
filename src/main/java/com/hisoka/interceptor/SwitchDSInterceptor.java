package com.hisoka.interceptor;

import com.hisoka.DBUtil.DataSourceSwitcher;
import com.hisoka.annotation.SwitchDS;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2017/5/12
 * @copyright: 2016 All rights reserved.
 */
public class SwitchDSInterceptor implements MethodInterceptor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String jdbcMasterKey;

    private String jdbcSlaverKey;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            // 这里会对同时查找了实现类所继承接口method上是否有此注解, 因此注解加在接口中还是实习类上都有效
            SwitchDS switchDS = AnnotationUtils.findAnnotation(invocation.getMethod(), SwitchDS.class);
            if(switchDS != null){
                if (StringUtils.isNotBlank(switchDS.key())){
                    //switch datasource
                    logger.debug("------------ switch datasource to {}------------", switchDS.key());
                    DataSourceSwitcher.setDataSourceKeyForceMaster(switchDS.key());
                } else if (switchDS.slaver()){
                    //switch datasource
                    logger.debug("------------ switch datasource to slaver {}------------", switchDS.key());
                    DataSourceSwitcher.setDataSourceKeyForceMaster(switchDS.key());
                } else {
                    // Do nothing
                }
            }
            return invocation.proceed();
        } finally{
            //switch to master datasource
            logger.debug("------------ switch datasorce back to master {}------------", jdbcMasterKey);
            DataSourceSwitcher.setDataSourceKeyForceMaster(jdbcMasterKey);
        }
    }

    public String getJdbcMasterKey() {
        return jdbcMasterKey;
    }

    public void setJdbcMasterKey(String jdbcMasterKey) {
        this.jdbcMasterKey = jdbcMasterKey;
    }

    public String getJdbcSlaverKey() {
        return jdbcSlaverKey;
    }

    public void setJdbcSlaverKey(String jdbcSlaverKey) {
        this.jdbcSlaverKey = jdbcSlaverKey;
    }
}
