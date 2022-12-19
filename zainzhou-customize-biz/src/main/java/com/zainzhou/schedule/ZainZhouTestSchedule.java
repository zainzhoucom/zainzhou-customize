package com.zainzhou.schedule;

import com.zainzhou.handler.AsyncTransactionalHandler;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author : 周振宇
 * Created on 2022/10/28 13:27
 **/
@Slf4j
@Component
public class ZainZhouTestSchedule {

    @Resource
    private AsyncTransactionalHandler asyncTransactionalHandler;

//    @Scheduled(fixedRateString = "10")
//    public void zainzhouTestTask(){
//        asyncTransactionalHandler.transactional1();
//    }
//
//    @Scheduled(fixedRateString = "15")
//    public void zainzhouTestTask2(){
//        asyncTransactionalHandler.transactional1();
//    }
//
//    @Scheduled(fixedRateString = "25")
//    public void zainzhouTestTask3(){
//        asyncTransactionalHandler.transactional1();
//    }

//    @PostConstruct
    public void testAction() throws Exception {
        log.info("action start");
        asyncTransactionalHandler.start1();
    }
}
