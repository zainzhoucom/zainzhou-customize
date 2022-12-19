package com.zainzhou.handler;

import com.zainzhou.dao.modules.RequestLogDO;
import com.zainzhou.dao.repository.RequestLogRepository;
import com.zainzhou.utils.RequestUserInfo;
import com.zainzhou.utils.RequestUtil;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author : 周振宇
 * Created on 2022/10/26 11:03
 **/
@Slf4j
@Component
public class RequestHandler {

    @Resource
    private RequestLogRepository repository;

    @Async("zainzhou")
    public void queryHeaderUser() {
        RequestUserInfo userInfo = RequestUtil.getUserInfo();
        log.info("userinfo:{}", userInfo);
        RequestLogDO logDO = RequestLogDO.builder().build();
        repository.insert(logDO);
    }
}
