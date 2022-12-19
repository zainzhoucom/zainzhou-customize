package com.zainzhou.handler;

import static org.mockito.Mockito.when;

import com.zainzhou.CustomizeApplication;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author : 周振宇
 * Created on 2022/11/15 09:38
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustomizeApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class MockTest {

    @Resource
    private ZainZhouTestHandler zainZhouTestHandler;

    @Test
    public void testLock() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            new Thread(() -> {
                zainZhouTestHandler.testRedisson(finalI);
            }).start();
        }

//        zainZhouTestHandler.testRedisson(1);

        Thread.sleep(100000);
    }
}
