package org.hinsteny.service.impl.MQ;

import org.hinsteny.service.MQService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Hinsteny
 * @date 2016/9/22
 * @copyright: 2016 All rights reserved.
 */
@Service
public class MQServiceImpl  implements MQService{

    @Autowired
    MyMessageReceiver receiver;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
    public boolean sendMessage(String message) {
        rabbitTemplate.convertAndSend(message);
        return true;
    }

    @Override
    public List<String> getMessage() {
        return receiver.getMessages();
    }
}
