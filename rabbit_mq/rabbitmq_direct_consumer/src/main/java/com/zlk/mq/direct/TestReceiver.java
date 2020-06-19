package com.zlk.mq.direct;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 接受者：从队列消费消息(error日志处理)
 *
 * @RabbitListener 绑定队列
 * <p>
 * autoDelete = "true" 临时队列，autoDelete = "false" 则开启持久化
 * type = ExchangeTypes.DIRECT 交换器类型 消息-订阅
 * key 路由键
 */
@Component
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(value = "${mq.config.queue.error22}", autoDelete = "true"),
                exchange = @Exchange(value = "${mq.config.exchange}", type = ExchangeTypes.DIRECT),
                key = "${mq.config.queue.error.routing.key}"
        )
)
public class TestReceiver {

    /**
     * 从消息队列接收error日志
     */
    @RabbitHandler
    public void process(String msg) {
        System.out.println("从消息队列接收error22日志：" + msg);
    }
}
