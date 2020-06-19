package com.zlk.mq.direct;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 接受者：从队列消费消息(Info日志处理)
 *
 * @RabbitListener 绑定队列
 * autoDelete = "true" 临时队列，autoDelete = "false" 则开启持久化
 * type = ExchangeTypes.DIRECT 交换器类型 消息-订阅
 * key 路由键
 */
@Component
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(value = "${mq.config.queue.info}", autoDelete = "true"),
                exchange = @Exchange(value = "${mq.config.exchange}", type = ExchangeTypes.DIRECT),
                key = "${mq.config.queue.info.routing.key}"
        )
)
public class InfoReceiver {

    /**
     * 从消息队列接收info日志
     */
    @RabbitHandler
    public void process(String msg) {
        System.out.println("从消息队列接收info日志：" + msg);
    }
}
