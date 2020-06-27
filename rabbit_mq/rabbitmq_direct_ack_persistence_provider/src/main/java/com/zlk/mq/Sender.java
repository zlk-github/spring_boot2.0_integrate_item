package com.zlk.mq;


import org.json.JSONObject;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 发送者:发送消息到队列
 */
@Component
public class Sender {

    /**
     * AmqpTemplate由spring提供
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${mq.config.exchange}")
    private String exchange;

    /**
     * 发送消息
     */
    public void send(Integer i) {
        //交换器必须是持久化。消费方配置
        //队列必须是持久化的。消费方配置
        //消息必须是持久化的。发送方配置
        try {
            //通过交换器与路由键  匹配   交换器与路由键绑定的消息队列
            //设置错误消息id,时间戳 全局唯一 实际消息的id；实际开发中选择订单或者业务id
          /*  String msgId = UUID.randomUUID().toString();
          Message errorMessage = MessageBuilder.withBody(("错误日志--"+i).getBytes()) //具体信息内容
                    .setDeliveryMode(MessageDeliveryMode.PERSISTENT)   // 消息持久化
                    .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)  //消息类型，如json等
                    .setCorrelationId(msgId).build();  //消息id,主要方便消费者重复消费时去重*/
         /*   MessageProperties properties = new MessageProperties();
            properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            properties.setCorrelationId(msgId);
            Message errorMessage =new Message(("错误日志--"+i).getBytes(), properties);
*/
         /*   CorrelationData date = new CorrelationData(msgId);
            // TODO 将 msgId 与 message 的关系保存起来,例如放到缓存中
            rabbitTemplate.convertAndSend(exchange, "log.error.routing.key", errorMessage, date);*/
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", "xx@163.com");
            jsonObject.put("timestamp", System.currentTimeMillis());
            String jsonString = jsonObject.toString();
            System.out.println("jsonString:" + jsonString);
            // 设置消息唯一id 保证每次重试消息id唯一
            Message message = MessageBuilder.withBody(jsonString.getBytes())
                    .setContentType(MessageProperties.CONTENT_TYPE_JSON).setContentEncoding("utf-8")
                    .setMessageId(UUID.randomUUID() + "").build(); //消息id设置在请求头里面 用UUID做全局ID
        } catch (Exception ex){
            System.out.println("连接mq失败！");
            ex.printStackTrace();
        }
    }

}
