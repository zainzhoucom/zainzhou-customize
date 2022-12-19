package com.zainzhou.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : 周振宇
 * Created on 2022/10/20 14:27
 **/
public class UtilsTest {

    public static AtomicInteger number = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            for (int i = 0; i < 100000000; i++) {
                number.getAndAdd(1);
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("执行结束:" + Thread.currentThread().getName());
        };

        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        t1.start();
        t2.start();

        Thread.sleep(1000);

        System.out.println("number  = " + number.get());
    }

}
