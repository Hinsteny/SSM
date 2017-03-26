package com.test.reflect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * @author Hinsteny
 * @Describtion
 * @date 2016/12/2
 * @copyright: 2016 All rights reserved.
 */
public class ScholarImpl implements Scholar {

    static final private Logger logger = LoggerFactory.getLogger(ScholarImpl.class);

    public static String printTime(){
        logger.info("Now time is {}", LocalDateTime.now().toString());
        return "Time";
    }

    @Override
    public String searching(String theme) {
        logger.info("Scholar do search {}", theme);
        return "success";
    }
}
