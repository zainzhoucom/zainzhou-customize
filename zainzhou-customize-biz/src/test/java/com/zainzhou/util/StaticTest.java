package com.zainzhou.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.Test;

/**
 * @author : 周振宇
 * Created on 2022/12/05 15:50
 **/
public class StaticTest {

    @Test
    public void testTimeFormat(){
        LocalDateTime localDateTime = LocalDateTime.now();
        String date = localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println(date);
    }
}
