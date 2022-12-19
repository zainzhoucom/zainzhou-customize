package com.zainzhou.config;

import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author : 周振宇
 * Created on 2022/10/26 14:41
 **/
@Configuration
public class AsyncTaskExecutorConfig {

    @Bean(name = "zainzhou")
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程池数量，方法: 返回可用处理器的Java虚拟机的数量。
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        //线程池维护线程的最大数量
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors() * 5);
        //线程池的队列容量
        executor.setQueueCapacity(Runtime.getRuntime().availableProcessors() * 2);
        executor.setKeepAliveSeconds(60);
        executor.setAwaitTerminationSeconds(60);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setThreadNamePrefix("shareTaskExecutor-");
        // for passing in request scope context
        executor.setTaskDecorator(new CustomTaskDecorator());
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
