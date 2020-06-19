package com.zlk.mq;

import org.springframework.amqp.core.AmqpTemplate;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.connection.CorrelationData;
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
    public void send() {
        //交换器必须是持久化。消费方配置
        //队列必须是持久化的。消费方配置
        //消息必须是持久化的。发送方配置
        try {
            //通过交换器与路由键  匹配   交换器与路由键绑定的消息队列
            for (int i = 1; i <= 20; i++) {
                Thread.sleep(1000);
                //设置错误消息id,时间戳 全局唯一 实际消息的id
                String errorLogId = String.valueOf(System.currentTimeMillis());
                //具体消息
                Message errorMessage = MessageBuilder.withBody(("错误日志........" + i).getBytes()).build();
                //消息进行持久化，
                errorMessage.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                System.out.println("MessageDeliveryMode:" + MessageDeliveryMode.PERSISTENT);
                //消息id
                errorMessage.getMessageProperties().getHeaders().put("errorLogId", errorLogId);
                rabbitTemplate.convertAndSend(this.exchange, "log.error.routing.key", errorMessage);

                //rabbitTemplate.convertAndSend(this.exchange, "log.info.routing.key", "普通日志......." + i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
