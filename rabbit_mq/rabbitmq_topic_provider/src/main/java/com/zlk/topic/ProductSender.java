package com.zlk.topic;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 商品发送者:发送消息到队列
 */
@Component
public class ProductSender {

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
        rabbitTemplate.convertAndSend(this.exchange, "product.log.info", "product.log.info发送内容.......");
        rabbitTemplate.convertAndSend(this.exchange, "product.log.debug", "product.log.debug发送内容.......");
        rabbitTemplate.convertAndSend(this.exchange, "product.log.warn", "product.log.warn发送内容.......");
        rabbitTemplate.convertAndSend(this.exchange, "product.log.error", "product.log.error发送内容.......");
    }

}
