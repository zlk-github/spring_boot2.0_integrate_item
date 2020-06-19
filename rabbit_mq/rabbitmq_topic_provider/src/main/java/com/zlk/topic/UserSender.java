package com.zlk.topic;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 用户发送者:发送消息到队列
 */
@Component
public class UserSender {

    /**
     * AmqpTemplate由spring提供
     */
    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("${mq.config.exchange}")
    private String exchange;

    /**
     * 发送消息
     */
    public void send() {
        //通过交换器与路由键  匹配   交换器与路由键绑定的消息队列
        rabbitTemplate.convertAndSend(this.exchange, "user.log.info", "user.log.info发送内容.......");
        rabbitTemplate.convertAndSend(this.exchange, "user.log.debug", "user.log.debug发送内容.......");
        rabbitTemplate.convertAndSend(this.exchange, "user.log.warn", "user.log.warn发送内容.......");
        rabbitTemplate.convertAndSend(this.exchange, "user.log.error", "user.log.error发送内容.......");
    }

}
