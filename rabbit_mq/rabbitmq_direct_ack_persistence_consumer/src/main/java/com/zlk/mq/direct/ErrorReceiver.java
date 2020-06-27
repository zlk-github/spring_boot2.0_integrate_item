package com.zlk.mq.direct;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

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

public class ErrorReceiver {

    /**
     * 从消息队列接收error日志
     */
    //@RabbitHandler
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "${mq.config.queue.error}", autoDelete = "false"),
                    exchange = @Exchange(value = "${mq.config.exchange}", type = ExchangeTypes.DIRECT),
                    key = "${mq.config.queue.error.routing.key}"
            )
    )
    public void process(Message message,Channel channel)  {
        //死信队列https://www.jianshu.com/p/9d496fde24b2
        System.out.println("从消息队列接收error日志：" + message);
          /*  try {
                System.out.println("从消息队列接收error日志：" + message);
                Integer s = 10/0;
                // 确认收到消息，false只确认当前consumer一个消息收到，true确认所有consumer获得的消息
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (Exception e) {
                //  https://www.cnblogs.com/piaolingzxh/p/5448927.html
                if (message.getMessageProperties().getRedelivered()) {
                    System.out.println("消息已重复处理失败,拒绝再次接收" );
                    //channel.basicReject(delivery.getEnvelope().getDeliveryTag(), false);
                   // deliveryTag:该消息的index
                   // requeue：被拒绝的是否重新入队列，requeue=false 表示不再重新入队，如果配置了死信队列则进入死信队列
                   // channel.basicNack 与 channel.basicReject 的区别在于basicNack可以拒绝多条消息，而basicReject一次只能拒绝一条消息
                    //channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
                    channel.basicReject(tag, false);
                } else {
                    System.out.println("消息即将再次返回队列处理");
                    //channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, true);
                    //deliveryTag:该消息的index
                    //multiple：是否批量.true:将一次性拒绝所有小于deliveryTag的消息。
                    //requeue：被拒绝的是否重新入队列，true重新入队
                    channel.basicNack(tag, false, true);
                }

               *//* channel.basicAck(deliveryTag,multiple);
                deliveryTag:该消息的index
                multiple：是否批量.true:将一次性ack所有小于deliveryTag的消息。*//*

                e.printStackTrace();
            }*/


        }
}
