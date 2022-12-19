package com.zainzhou.controller;

import com.zainzhou.annotation.RedisLock;
import com.zainzhou.mq.message.DemoMqMessage;
import com.zainzhou.mq.publisher.CustomizePublisher;
import com.zainzhou.reqeust.SendMessageRequest;
import com.zainzhou.utils.Result;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : 周振宇
 * Created on 2022/10/19 17:17
 **/
@RestController
@RequestMapping("/zainzhou/mq/")
public class RocketMqDemoController {

    @Resource
    private CustomizePublisher customizePublisher;

    @RedisLock(key = "send-message", dynamicKey = true, dynamicParam = "#request.id")
    @PostMapping("send-message")
    public Result<String> sendMessage(@RequestBody @Validated SendMessageRequest request){
        customizePublisher.send(DemoMqMessage.builder()
                        .id(request.getId())
                        .now(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .build());
        return Result.success("send success");
    }
}
