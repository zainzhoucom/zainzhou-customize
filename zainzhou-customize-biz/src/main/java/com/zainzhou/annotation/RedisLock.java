package com.zainzhou.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : 周振宇
 * Created on 2022/09/29 15:01
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisLock {

    /**
     * 锁定时间，超时后解除锁定，默认60秒
     * @return
     */
    long timeOut() default 60;

    /**
     * 获取锁等待时间，默认3秒
     * @return
     */
    long waitTIme() default 3;

    /**
     * redis key
     * @return
     */
    String key();

    boolean throwException() default false;

    /**
     * 使用参数动态拼接key
     * @return
     */
    boolean dynamicKey() default false;

    /**
     * 动态表达式
     * @return
     */
    String dynamicParam() default "";

}
