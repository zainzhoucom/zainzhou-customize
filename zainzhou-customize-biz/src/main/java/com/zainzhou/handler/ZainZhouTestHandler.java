package com.zainzhou.handler;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.zainzhou.annotation.RedisLock;
import com.zainzhou.dao.modules.TblZainZhouTestDO;
import com.zainzhou.dao.repository.TblZainZhouTestRepository;
import com.zainzhou.event.model.AsyncTransactionModel;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author : 周振宇
 * Created on 2022/10/28 11:26
 **/
@Slf4j
@Component
public class ZainZhouTestHandler {

    @Resource
    private TblZainZhouTestRepository zainZhouTestRepository;

    @RedisLock(key = "ZAINZHOUTESTHANDLER_TESTUPDATE")
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void testUpdate(AsyncTransactionModel model) {
//        String transactionalName = TransactionSynchronizationManager.getCurrentTransactionName();
        log.info("incr:{}", model.getIncr());
        TblZainZhouTestDO m1 = zainZhouTestRepository.assertIdExist(model.getM1Id());
        if(Objects.isNull(m1)){
            log.error("m1:{} is null", model.getM1Id());
            return;
        }else if(!m1.getContext().equals(model.getM1Context())){
            log.error("m1 context:{} not equals", model.getM1Context());
            return;
        }

        m1.setContext(IdWorker.getIdStr());
        m1.setUpdateTime(LocalDateTime.now());
        m1.setUpdateName("test1");
        m1.setUpdateBy("test1");
        zainZhouTestRepository.updateSelectiveById(m1);

//        TblZainZhouTestDO m2 = zainZhouTestRepository.assertIdExist(model.getM2Id());
//        if(Objects.isNull(m2)){
//            log.error("m2:{} is null", model.getM1Id());
//        }else if(!m2.getContext().equals(model.getM2Context())){
//            log.error("m2 context:{} not equals", model.getM2Context());
//        }
//
//        TblZainZhouTestDO m3 = zainZhouTestRepository.assertIdExist(model.getM3Id());
//        if(Objects.isNull(m3)){
//            log.error("m3:{} is null", model.getM3Id());
//        }else if(!m3.getContext().equals(model.getM3Context())){
//            log.error("m3 context:{} not equals", model.getM3Context());
//        }
    }

    @RedisLock(key = "ZAINZHOU_TEST_LOCK", waitTIme = 0, timeOut = 0)
    public void testRedisson(int count){
        try {
            log.info("进入锁:{}",count);
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("sleep error",e);
        }finally {
            log.info("结束：{}", count);
        }
    }
}
