package com.zainzhou.mq.publisher;

import com.zainzhou.mq.MqConfig;
import com.zainzhou.mq.RocketMqService;
import com.zainzhou.mq.message.DemoMqMessage;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.stereotype.Component;

/**
 * @author : 周振宇
 * Created on 2022/09/29 16:34
 **/
@Slf4j
@Component
public class CustomizePublisher {

    @Resource
    private RocketMqService rocketMqService;


    public void send(DemoMqMessage message){
        SendResult sendResult = rocketMqService.asyncSend(MqConfig.DEMO_MQ_TOPIC, message);
        log.info("发送测试消息：{}", sendResult);
    }
}
