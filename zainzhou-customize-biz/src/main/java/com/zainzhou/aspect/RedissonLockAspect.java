package com.zainzhou.aspect;

import com.zainzhou.annotation.RedisLock;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

/**
 * @author : 周振宇
 * Created on 2022/09/29 15:02
 **/
@Slf4j
@Aspect
@Component
public class RedissonLockAspect {

    @Resource
    private RedissonClient redissonClient;

    @Value("${spring.application.name}")
    private String prefix;

    private final SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
    /**
     * 用于获取方法参数定义名字.
     */
    private final DefaultParameterNameDiscoverer defaultParameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    @Pointcut("@annotation(redisLock)")
    public void lockPointCut(RedisLock redisLock) {

    }

    @Around(value = "lockPointCut(redisLock)", argNames = "joinPoint, redisLock")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint, RedisLock redisLock) throws Throwable {
        String key = prefix + ":" + redisLock.key();
        if (redisLock.dynamicKey()) {
            key = prefix + ":" + redisLock.key() + getKeyBySpeL(redisLock.dynamicParam(), joinPoint);
        }
        log.info("进入需锁定方法：{}", key);
        long begin = System.currentTimeMillis();
        Object result;
        RLock lock = redissonClient.getLock(key);
        try {
            boolean rLock = lock.tryLock(redisLock.waitTIme(), redisLock.timeOut(), TimeUnit.SECONDS);
            if (!rLock) {
                log.info("锁定方法暂未执行结束");
                if (redisLock.throwException()) {
                    throw new Exception("未能获取到锁" + key);
                }
            }
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("执行锁定方法出错", throwable);
            throw throwable;
        } finally {
            long end = System.currentTimeMillis();
            log.info("执行结束:{}，耗时：{}ms，解除锁定", key, end - begin);
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
        return result;
    }

    public String getKeyBySpeL(String spel, ProceedingJoinPoint proceedingJoinPoint) throws Exception {
        log.info("动态构建分布式锁key，表达式：{}", spel);
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        String[] paramNames = defaultParameterNameDiscoverer.getParameterNames(methodSignature.getMethod());
        if (null == paramNames) {
            throw new Exception("构建动态参数Key入参无效");
        }
        EvaluationContext context = new StandardEvaluationContext();
        Object[] args = proceedingJoinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            context.setVariable(paramNames[i], args[i]);
        }
        return String.valueOf(spelExpressionParser.parseExpression(spel).getValue(context));
    }
}
