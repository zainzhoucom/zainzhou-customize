package com.zainzhou.utils.threadpool;

import java.lang.Thread.UncaughtExceptionHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : 周振宇
 * Created on 2022/11/17 16:17
 **/
@Slf4j
public class MyUncaughtExceptionHandler implements UncaughtExceptionHandler {

    public static UncaughtExceptionHandler getInstance(){
        return new MyUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.error("thread {} com.fofund.bus.capital.exception exit", t.getName(), e);
    }
}
