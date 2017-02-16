package com.test.base;


import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Hinsteny
 * @Describtion
 * @date 2017/2/16
 * @copyright: 2016 All rights reserved.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@Rollback
@Transactional(transactionManager = "transactionManager")
@ContextConfiguration(locations = {"classpath:spring/spring-context.xml", "classpath:spring/spring-servlet.xml"  })
public class SpringContext extends NormalBase{

    protected Logger logger = LoggerFactory.getLogger(getClass());
}