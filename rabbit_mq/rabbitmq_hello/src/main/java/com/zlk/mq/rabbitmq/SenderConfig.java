package com.zlk.mq.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SenderConfig {

    //持久化队列
    @Bean
    public Queue queue() {
        //hello-mq-queue为队列名称
        return new Queue("hello-mq-queue");
    }

}
