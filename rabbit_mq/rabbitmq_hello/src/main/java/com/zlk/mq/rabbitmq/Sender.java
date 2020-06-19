package com.zlk.mq.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 发送者:发送消息到队列
 */
@Component
public class Sender {

    /**
     * AmqpTemplate由spring提供
     */
    @Autowired
    private AmqpTemplate rabbitTemplate;

    /**
     * 发送消息
     */
    public void send() {
        String msg = "你好，这是一条消息";
        //将消息发送到队列hello-mq-queue
        for (int i = 1; i <= 20; i++) {
            rabbitTemplate.convertAndSend("hello-mq-queue", msg + "---" + i);
        }
    }

}
