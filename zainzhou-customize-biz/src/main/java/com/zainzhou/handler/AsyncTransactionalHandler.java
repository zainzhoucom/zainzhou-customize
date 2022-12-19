package com.zainzhou.handler;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.zainzhou.annotation.RedisLock;
import com.zainzhou.dao.modules.TblZainZhouTestDO;
import com.zainzhou.dao.repository.TblZainZhouTestRepository;
import com.zainzhou.event.Action2Event;
import com.zainzhou.event.ZainZhouTestEvent;
import com.zainzhou.event.model.AsyncTransactionModel;
import com.zainzhou.mq.RocketMqService;
import com.zainzhou.utils.RedisService;
import com.zainzhou.utils.TransactionalUtils;
import com.zainzhou.utils.threadpool.ExecutorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author : 周振宇
 * Created on 2022/10/27 19:19
 **/
@Slf4j
@Component
public class AsyncTransactionalHandler {

    @Resource
    private TblZainZhouTestRepository zainZhouTestRepository;
    @Resource
    private ApplicationContext applicationContext;
    @Resource
    private RedisService redisService;
    @Resource
    private ZainZhouTestHandler zainZhouTestHandler;

    private static final String EXECUTE_SUPERPOSITION_KEY = "EXECUTE_CREATE_TEST_RECORD_SUPERPOSITION";

    @Transactional(rollbackFor = Exception.class)
    @RedisLock(key = "LOCK:ZAINZHOU:ACTION_3")
    public void createTestRecord(AsyncTransactionModel model){
        log.info("action 3");
        TblZainZhouTestDO m1 = zainZhouTestRepository.assertIdExist(model.getM1Id());
        if(Objects.isNull(m1)){
            log.error("action 3 m1:{} is null", model.getM1Id());
        }else if(!m1.getContext().equals(model.getM1Context())){
            log.error("action 3 m1 context:{} not equals", model.getM1Context());
        }

        Long incr = redisService.incr(EXECUTE_SUPERPOSITION_KEY, 1L);
        log.info("start incr:{}", incr);
        model.setIncr(incr);
        doSave(model);
        log.info("{}",model);
        applicationContext.publishEvent(new ZainZhouTestEvent(model));
//        throw new RuntimeException("test");
//        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
//            @Override
//            public void afterCommit() {
//                applicationContext.publishEvent(new ZainZhouTestEvent(model));
//            }
//        });
//        log.info("{} end", incr);
    }


    private void doSave(AsyncTransactionModel model){

        TblZainZhouTestDO m3 = TblZainZhouTestDO.builder()
                .context(model.getM3Context())
                .build();
        model.setM3Id(zainZhouTestRepository.insert(m3));
    }

    @Async
    public void startAction() throws InterruptedException {
        Thread.sleep(10000);
        int count = 0;
        do {
            count++;
            applicationContext.getBean(AsyncTransactionalHandler.class).transactional1();
            Thread.sleep(0);
        } while (count != Integer.MAX_VALUE);
    }

    @Async
    public void start1() throws Exception {
        Thread.sleep(10000);
        int count = 0;
        do {
            count++;
            applicationContext.getBean(AsyncTransactionalHandler.class).transactional2();
            Thread.sleep(0);
        } while (count != Integer.MAX_VALUE);
    }

    @RedisLock(key = "LOCK:ZAINZHOU:ACTION_START")
    public void transactional2(){
        AsyncTransactionModel model = AsyncTransactionModel.builder()
                .m1Context(IdWorker.getIdStr())
                .m2Context(IdWorker.getIdStr())
                .m3Context(IdWorker.getIdStr())
                .build();
        TblZainZhouTestDO m1 = TblZainZhouTestDO.builder()
                .context(model.getM1Context())
                .build();
        model.setM1Id(zainZhouTestRepository.insert(m1));

        applicationContext.publishEvent(new ZainZhouTestEvent(model));
    }

    @RedisLock(key = "LOCK:ZAINZHOU:ACTION_START")
//    @Transactional(rollbackFor = Exception.class)
    public void transactional1(){
//        String transactionalName = TransactionSynchronizationManager.getCurrentTransactionName();
//        TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
//        log.info("action 1:{}, status:{}", transactionalName, status.isCompleted());
        AsyncTransactionModel model = AsyncTransactionModel.builder()
                .m1Context(IdWorker.getIdStr())
                .m2Context(IdWorker.getIdStr())
                .m3Context(IdWorker.getIdStr())
                .build();
        TblZainZhouTestDO m1 = TblZainZhouTestDO.builder()
                .context(model.getM1Context())
                .build();
        model.setM1Id(zainZhouTestRepository.insert(m1));
        doAction2(model);
    }

//    @Async
    public void transactional3() throws Exception {
        List<TblZainZhouTestDO> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            TblZainZhouTestDO m1 = TblZainZhouTestDO.builder()
                    .context(IdWorker.getIdStr())
                    .build();
            m1.setId(IdWorker.getIdStr());
            list.add(m1);
        }
        TransactionalUtils.required(() -> {
            zainZhouTestRepository.insertBatch(list);
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    List<AsyncTransactionModel> modelList = new ArrayList<>();
                    list.forEach(c -> {
                        AsyncTransactionModel model = AsyncTransactionModel.builder()
                                .m1Id(c.getId())
                                .m1Context(c.getContext())
                                .build();
                        zainZhouTestHandler.testUpdate(model);
                        modelList.add(model);
                    });
                    applicationContext.getBean(AsyncTransactionalHandler.class).transfer4(modelList);
                }
            });

        });
    }

    private void doAction2(AsyncTransactionModel model){
        applicationContext.getBean(AsyncTransactionalHandler.class).action2(model);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @RedisLock(key = "LOCK:ZAINZHOU:ACTION_2")
    public void action2(AsyncTransactionModel model){
//        TblZainZhouTestDO m2 = TblZainZhouTestDO.builder()
//                .context(model.getM2Context())
//                .build();
//        model.setM2Id(zainZhouTestRepository.insert(m2));

        TblZainZhouTestDO m1 = zainZhouTestRepository.assertIdExist(model.getM1Id());
        if(Objects.isNull(m1)){
            log.error("m1:{} is null", model.getM1Id());
        }else if(!m1.getContext().equals(model.getM1Context())){
            log.error("m1 context:{} not equals", model.getM1Context());
        }
//        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
//            @Override
//            public void afterCommit() {
////                String transactionalName = TransactionSynchronizationManager.getCurrentTransactionName();
////                TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
////                log.info("action 2:{}, status:{}", transactionalName, status.isCompleted());
//                applicationContext.publishEvent(new Action2Event(model));
//            }
//        });

    }
    @Async
    @RedisLock(key = "DAILY_FINISH_TRADE_MATCH_TASK")
    public void transfer4(List<AsyncTransactionModel> modelList){
        log.info("read update");
        List<TblZainZhouTestDO> list = zainZhouTestRepository.assertIdsExist(modelList.stream().map(AsyncTransactionModel::getM1Id).collect(Collectors.toList()));
        long count = list.stream().filter(c -> "test1".equals(c.getUpdateBy())).count();
        if(modelList.size() != count){
            log.error("read update incomplete");
        }
    }
}
