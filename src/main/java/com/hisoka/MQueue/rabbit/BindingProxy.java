package com.hisoka.MQueue.rabbit;

import org.springframework.amqp.core.*;

/**
 * @author Hinsteny
 * @date 2016/9/23
 * @copyright: 2016 All rights reserved.
 */
public class BindingProxy {

    private Queue queue;

    private TopicExchange exchange;

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    public void setExchange(TopicExchange exchange) {
        this.exchange = exchange;
    }

    public Binding getBinding(){
        return BindingBuilder.bind(queue).to(exchange).with(queue.getName());
    }
}
