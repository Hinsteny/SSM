package org.hinsteny.aspectJ;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2016/12/1
 * @copyright: 2016 All rights reserved.
 */
@Aspect
public class SystemArchitecture {

    private static final Logger logger = LoggerFactory.getLogger(SystemArchitecture.class);
    /**
     * A join point is in the web layer if the method is defined
     * in a type in the com.xyz.someapp.web package or any sub-package
     * under that.
     */
    @Pointcut("within(org.hinsteny.action.*)")
    public void inWebLayer() {
    }

    /**
     * A join point is in the service layer if the method is defined
     * in a type in the com.xyz.someapp.service package or any sub-package
     * under that.
     */
    @Pointcut("within(org.hinsteny.service.*)")
    public void inServiceLayer() {
    }

    /**
     * A join point is in the data access layer if the method is defined
     * in a type in the com.xyz.someapp.dao package or any sub-package
     * under that.
     */
    @Pointcut("within(org.hinsteny.repository.*)")
    public void inDataAccessLayer() {
    }

}