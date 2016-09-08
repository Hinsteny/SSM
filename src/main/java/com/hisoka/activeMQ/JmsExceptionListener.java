package com.hisoka.activeMQ;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;

import org.springframework.stereotype.Component;

/**
 * @author Hinsteny
 * @date 2016/9/7
 * @copyright: 2016 All rights reserved.
 */
//@Component
public class JmsExceptionListener implements ExceptionListener {

    public void onException(final JMSException e) {
        e.printStackTrace();
    }
}
