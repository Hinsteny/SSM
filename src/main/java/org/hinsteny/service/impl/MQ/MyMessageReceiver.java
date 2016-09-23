package org.hinsteny.service.impl.MQ;

import com.hisoka.MQueue.rabbit.MessageListenerProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hinsteny
 * @date 2016/9/23
 * @copyright: 2016 All rights reserved.
 */
@Component("messageReceiver")
public class MyMessageReceiver extends MessageListenerProxy{

    private static final Logger logger = LoggerFactory.getLogger(MyMessageReceiver.class);

    private volatile List<String> messages = new ArrayList(64);

    public List<String> getMessages() {
        List<String> result = new ArrayList(messages);
        this.messages.clear();
        return result;
    }

    @Override
    public Object handleMessage(String messageId, String messageContent, String queue) {
        if (logger.isInfoEnabled()) logger.info("Receive message id {}, queue {} and content is {}", messageId, queue, messageContent);
        this.messages.add(messages.toString());
        return true;
    }
}
