package com.zlk.mq.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * AmqpTemplate confirm确认配置类。主要监控是否成功到交换器。是否从交换器成功路由到队列。
 */
@Configuration
public class RabbitTemplateconfig {
    /**
     * 优先注入当前bean
     * @param connectionFactory connectionFactory
     * @return AmqpTemplate
     */
    @Primary
    @Bean
    public RabbitTemplate createAmqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);

        //mandatory：交换器无法根据自身类型和路由键找到一个符合条件的队列时的处理方式
        //true：RabbitMQ会调用Basic.Return命令将消息返回给生产者
        //false：RabbitMQ会把消息直接丢弃
        rabbitTemplate.setMandatory(true);

        //交换器确认
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback(){
            /**
             * 交换器回调
             * @param correlationData 消息唯一标识
             * @param ack true 成功 / false 失败
             * @param cause 失败原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                //System.out.println("消息唯一标识 = [" + correlationData + "], ack = [" + ack + "], 错误原因 = [" + cause + "]");
                if (ack){
                   // System.out.println("消息发送到交换器成功！");
                }else{
                    System.out.println("消息发送到交换器失败-------消息唯一标识 = [" + correlationData + "],失败原因："+cause);
                }
            }
        });

        //路由到队列确认（路由到队列失败时调用）
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            /**
             * 队列回调（路由队列失败）
             * @param message 消息
             * @param replyCode 回应码
             * @param replyText  回应消息
             * @param exchange 交换器
             * @param routingKey 路由键
             */
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("消息路由到队列失败-----消息 = [" + message + "], 回应码 = [" + replyCode + "], 回应消息 = [" + replyText + "], 交换器 = [" + exchange + "], 路由键 = [" + routingKey + "]");
                //失败涉及重试
            }
        });

        return rabbitTemplate;
    }
}
