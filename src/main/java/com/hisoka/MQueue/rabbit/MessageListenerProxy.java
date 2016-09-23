package com.hisoka.MQueue.rabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * MessageListener Proxy
 *
 * @author Hinsteny
 * @date 2016/9/22
 * @copyright: 2016 All rights reserved.
 */
public abstract class MessageListenerProxy implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(MessageListenerProxy.class);

    private static final String DEFAULT_CHARSET = "UTF-8";

    public abstract Object handleMessage(String messageId, String messageContent, String queue);

    @Override
    public void onMessage(Message message) {
        try {
            String messageId = message.getMessageProperties().getMessageId();
            String messageContent = new String(message.getBody(), DEFAULT_CHARSET);
            String queue = message.getMessageProperties().getReceivedRoutingKey();
            handleMessage(messageId, messageContent, queue);
        } catch (Throwable t) {
            logger.error("Handle mq message error, message=" + message, t);
        }
    }


}
