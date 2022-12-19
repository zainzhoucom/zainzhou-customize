package com.zainzhou.event.listener;

import com.zainzhou.event.ZainZhouTestEvent;
import com.zainzhou.event.model.AsyncTransactionModel;
import com.zainzhou.handler.ZainZhouTestHandler;
import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author : 周振宇
 * Created on 2022/10/28 11:18
 **/
@Slf4j
@Component
public class ZainZhouTestListener implements ApplicationListener<ZainZhouTestEvent> {

    @Resource
    private ZainZhouTestHandler zainZhouTestHandler;


    @Async
    @Override
    public void onApplicationEvent(@NotNull ZainZhouTestEvent zainZhouTestEvent) {
        zainZhouTestHandler.testUpdate((AsyncTransactionModel) zainZhouTestEvent.getSource());
    }

//    @TransactionalEventListener(
//            phase = TransactionPhase.AFTER_COMMIT
//    )
//    @EventListener
//    public void onApplicationEvent(@NonNull ZainZhouTestEvent zainZhouTestEvent){
//
//    }

//    @TransactionalEventListener(
//            phase = TransactionPhase.BEFORE_COMMIT
//    )
//    public void test2(@NonNull ZainZhouTestEvent zainZhouTestEvent){
//        try {
//            String transactionalName = TransactionSynchronizationManager.getCurrentTransactionName();
//            TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
//            log.info("action 3:{}, status:{}", transactionalName, status.isCompleted());
//        }catch (Exception e){
//            l
//        }
//
//    }
}
