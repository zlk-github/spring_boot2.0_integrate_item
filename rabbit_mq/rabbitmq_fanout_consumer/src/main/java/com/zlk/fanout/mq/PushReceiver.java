package com.zlk.fanout.mq;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 接受者：从队列消费消息(push日志处理)
 *
 * @RabbitListener 绑定队列，路由键、交换器
 * * autoDelete = "true" 临时队列，autoDelete = "false" 则开启持久化
 * type = ExchangeTypes.FANOUT 交换器类型 广播形式，
 * 不需要路由键key
 */
@Component
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(value = "${mq.config.queue.push}", autoDelete = "true"),
                exchange = @Exchange(value = "${mq.config.exchange}", type = ExchangeTypes.FANOUT)
        )
)
public class PushReceiver {

    /**
     * 从消息队列接收push日志
     */
    @RabbitHandler
    public void process(String msg) {
        System.out.println("push处理：" + msg);
    }
}
