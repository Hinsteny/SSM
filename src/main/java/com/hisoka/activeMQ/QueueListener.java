package com.hisoka.activeMQ;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.stereotype.Component;

/**
 * @author Hinsteny
 * @date 2016/9/7
 * @copyright: 2016 All rights reserved.
*/
@Component
public class QueueListener implements MessageListener {

    public void onMessage( final Message message ) {
        if ( message instanceof TextMessage ) {
            final TextMessage textMessage = (TextMessage) message;
            try {
                System.out.println( textMessage.getText() );
            } catch (final JMSException e) {
                e.printStackTrace();
            }
        }
    }
}