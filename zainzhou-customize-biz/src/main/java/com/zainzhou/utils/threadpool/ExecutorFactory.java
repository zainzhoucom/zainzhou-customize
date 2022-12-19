package com.zainzhou.utils.threadpool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * @author : 周振宇
 * Created on 2022/11/17 16:20
 **/
public class ExecutorFactory {

    private ExecutorFactory(){}
    private static final Map<String, ExecutorService> EXECUTOR_MAP = new HashMap<>();

    public static final String INSTANCE_GLOBAL = "GlobalExecutorThreads";

    static {
        EXECUTOR_MAP.put(INSTANCE_GLOBAL, CustomExecutors.create(INSTANCE_GLOBAL, Runtime.getRuntime().availableProcessors() * 2, 50));
    }

    public static ExecutorService getInstance(String instanceType) {
        ExecutorService instance = EXECUTOR_MAP.get(instanceType);
        if (instance != null) {
            return instance;
        }
        throw new RuntimeException("invalid executor instance type----"+instanceType);
    }
}
