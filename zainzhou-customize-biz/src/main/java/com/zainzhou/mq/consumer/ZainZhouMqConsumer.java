package com.zainzhou.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.zainzhou.mq.MqConfig;
import com.zainzhou.mq.RocketMqService;
import com.zainzhou.mq.message.DemoMqMessage;
import java.nio.charset.StandardCharsets;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author : 周振宇
 * Created on 2022/10/19 16:06
 **/
@Slf4j
@Component
public class ZainZhouMqConsumer {

    @Value("${rocketmq.name-server}")
    private String serverAddress;
    @Resource
    private RocketMqService rocketMqService;

    @PostConstruct
    public void demoConsumer(){
        rocketMqService.consumerMessageListener("zainzhou-customize-demo-consumer", serverAddress,MqConfig.DEMO_MQ_TOPIC, (messageList, context) -> {
            for (MessageExt messageExt : messageList) {
                String messageBody = new String(messageExt.getBody(), StandardCharsets.UTF_8);
                try {
                    log.info("收到测试MQ消息：{}", messageBody);
                    DemoMqMessage message = JSON.parseObject(messageBody, DemoMqMessage.class);
                    log.info("id:{}", message.getId());
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }catch (Exception e){
                    log.error("消费失败：{}", messageBody, e);
                    log.warn("消费失败：{}", messageBody);
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
    }
}
