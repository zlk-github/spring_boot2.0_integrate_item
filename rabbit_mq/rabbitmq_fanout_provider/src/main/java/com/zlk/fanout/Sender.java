package com.zlk.fanout;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * 订单发送者:发送消息到队列
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
    public void send() {
        //广播不要路由键，直接将消息绑定到交换器上
        for (int i = 1; i <= 20; i++) {
            rabbitTemplate.convertAndSend(this.exchange, "", "订单提交成功--" + i);
        }
    }

}
