package com.zainzhou.utils.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : 周振宇
 * Created on 2022/11/17 16:16
 **/
@Slf4j
public class CustomExecutors {
    private CustomExecutors(){}
    public static ExecutorService create(String prefix, int corePoolSize, int maximumPoolSize) {
        return create(prefix, corePoolSize, maximumPoolSize, 100000,
                (r, executor) -> log.error("{}-executor-queue-overflow... detail: {}", prefix, executor)
        );
    }

    public static ExecutorService create(String prefix, int corePoolSize, int maximumPoolSize, int queueSize, RejectedExecutionHandler rejectedExecutionHandler) {
        return CustomExecutors.create(prefix, corePoolSize, maximumPoolSize, 100L, TimeUnit.SECONDS, queueSize, rejectedExecutionHandler);
    }

    public static ExecutorService create(String prefix, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, int queueSize) {

        return new ThreadPoolExecutor(
                corePoolSize, maximumPoolSize,
                keepAliveTime, unit,
                new ArrayBlockingQueue<>(queueSize),
                new CustomNamedThreadFactory(prefix),
                (r, executor) -> log.error("{}-executor-queue-overflow... detail: {}", prefix, executor));
    }

    public static ExecutorService create(String prefix, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
            int queueSize, RejectedExecutionHandler rejectedExecutionHandler) {

        return new ThreadPoolExecutor(
                corePoolSize, maximumPoolSize,
                keepAliveTime, unit,
                new ArrayBlockingQueue<>(queueSize),
                new CustomNamedThreadFactory(prefix),
                rejectedExecutionHandler);
    }
}
