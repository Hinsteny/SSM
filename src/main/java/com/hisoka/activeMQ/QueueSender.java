package com.hisoka.activeMQ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Hinsteny
 * @date 2016/9/6
 * @copyright: 2016 All rights reserved.
 */
//@Component
public class QueueSender {

    private final JmsTemplate jmsTemplate;

    @Autowired
    public QueueSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void send(final String destinationName, final String message) {
        jmsTemplate.convertAndSend(destinationName, message );
    }
}
