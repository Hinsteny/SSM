package org.hinsteny.service.impl.MQ;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author Hinsteny
 * @date 2016/9/22
 * @copyright: 2016 All rights reserved.
 */
//@Component("messageReceiver")
public class MessageReceiver {

    private volatile List<String> messages = new ArrayList(64);

    private CountDownLatch latch = new CountDownLatch(1);

    public List<String> getMessages() {
        List<String> result = new ArrayList(messages);
        this.messages.clear();
        return result;
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public void handleMessage(String message) {
        this.messages.add(messages.toString());
        latch.countDown();
    }
}
