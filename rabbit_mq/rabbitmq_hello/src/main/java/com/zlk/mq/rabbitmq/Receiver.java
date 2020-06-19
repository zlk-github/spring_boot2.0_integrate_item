package com.zlk.mq.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 接受者：从队列消费消息
 */
@Component
public class Receiver {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    /**
     * 从消息队列接收消息
     */
    @RabbitListener(queues = "hello-mq-queue")
    public void process(String msg) {
        System.out.println("receiver" + msg);
    }
}
