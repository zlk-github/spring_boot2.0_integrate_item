package com.zlk.topic.mq;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 接受者：从队列消费消息(error日志处理)
 *
 * @RabbitListener 绑定队列
 * autoDelete = "true" 临时队列，autoDelete = "false" 则开启持久化
 * type = ExchangeTypes.TOPIC 交换器类型 主题
 * key = "*.log.error" 路由键模糊匹配
 */
@Component
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(value = "${mq.config.queue.error}", autoDelete = "true"),
                exchange = @Exchange(value = "${mq.config.exchange}", type = ExchangeTypes.TOPIC),
                key = "*.log.error"
        )
)
public class ErrorReceiver {

    /**
     * 从消息队列接收error日志
     */
    @RabbitHandler
    public void process(String msg) {
        System.out.println("----------error------------从消息队列接收error日志" + msg);
    }
}
