package com.zainzhou.mq;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

/**
 * @author : 周振宇
 * Created on 2022/09/29 15:47
 **/
@Slf4j
@Service
public class RocketMqService {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public SendResult asyncSend(String destination, Object payload){
        return rocketMQTemplate.syncSend(destination, payload);
    }

    public void asyncSend(String destination, Message<?> message, SendCallback sendCallback) {
        rocketMQTemplate.asyncSend(destination, message, sendCallback);
    }

    public void asyncSend(String destination, Object payload, SendCallback sendCallback) {
        rocketMQTemplate.asyncSend(destination, payload, sendCallback);
    }

    public SendResult syncSendOrderly(String destination, Message<?> message, String hashKey) {
        return rocketMQTemplate.syncSendOrderly(destination, message, hashKey);
    }

    public SendResult syncSendOrderly(String destination, Message<?> message, String hashKey, long timeout) {
        return rocketMQTemplate.syncSendOrderly(destination, message, hashKey, timeout);
    }

    public void asyncSendOrderly(String destination, Message<?> message, String hashKey, SendCallback sendCallback) {
        rocketMQTemplate.asyncSendOrderly(destination, message, hashKey, sendCallback);
    }

    public void consumerMessageListener(String consumerGroup, String serverAddr, String topicName,
            MessageListenerConcurrently listenerConcurrently) {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(serverAddr);
        try {
            consumer.subscribe(topicName, "*");
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
            consumer.registerMessageListener(listenerConcurrently);
            consumer.start();
        } catch (Exception e) {
            log.error("rocketMq消息消费时异常", e);
        }
    }
}
