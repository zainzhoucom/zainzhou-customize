package com.zainzhou.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : 周振宇
 * Created on 2022/11/17 17:56
 **/
@Slf4j
@Component
public class TransactionalComponent {
    /**
     * Transactional run
     */
    public interface Cell{

        /**
         * Transactional run
         * @throws Exception
         */
        void run() throws Exception;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void required(Cell cell) throws Exception{
        log.info(">>>>>>>>> Transactional Start");
        cell.run();
        log.info("<<<<<<<<< Transactional End");
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void requiredNew(Cell cell) throws Exception{
        log.info(">>>>>>>>> Transactional Start");
        cell.run();
        log.info("<<<<<<<<< Transactional End");
    }
}
