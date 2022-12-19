package com.zainzhou.event.listener;

import com.zainzhou.event.ZainZhouTestEvent;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author : 周振宇
 * Created on 2022/10/28 15:10
 **/
@Slf4j
@Component
public class TestOrdinaryListener implements ApplicationListener<ZainZhouTestEvent> {

    @Async
    @Override
    public void onApplicationEvent(@NotNull ZainZhouTestEvent zainZhouTestEvent) {
        log.debug("{}", zainZhouTestEvent.getSource());
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
