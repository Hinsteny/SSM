package com.test.base;

import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalTime;

/**
 *
 * @author Hinsteny
 * @Describtion
 * @date 2017/2/16
 * @copyright: 2016 All rights reserved.
 */
public class NormalBase {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected LocalTime start;
    protected LocalTime end;

    @Before
    public  void start() throws Exception {
        start = LocalTime.now();
//        Instant.now();
    }

    @After
    public  void finish() throws Exception {
        end = LocalTime.now();
        Duration duration = Duration.between(start, end);
        logger.info("The test cost time is: {}", duration.toString());
    }

}
