package com.zlk.mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${mq.config.exchange}")
    private String exchange;

    /**
     * 发送消息
     */
    public void send() throws InterruptedException {
        //通过交换器与路由键  匹配   交换器与路由键绑定的消息队列
        for (int i = 1; i <= 20; i++) {
            Thread.sleep(1000);
            rabbitTemplate.convertAndSend(this.exchange, "log.error.routing.key", "错误日志........" + i);
            rabbitTemplate.convertAndSend(this.exchange, "log.info.routing.key", "普通日志......." + i);
        }
    }

}
