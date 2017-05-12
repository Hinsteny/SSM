package com.hisoka.test;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2017/5/12
 * @copyright: 2016 All rights reserved.
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-context.xml", "classpath:spring/spring-servlet.xml"  })
@WebAppConfiguration
public class BaseUnTransactionalTest {

}
