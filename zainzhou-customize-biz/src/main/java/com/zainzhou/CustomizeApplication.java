package com.zainzhou;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author : 周振宇
 * Created on 2022/09/28 11:36
 **/
@Slf4j
@EnableAsync
@EnableScheduling
@EnableDiscoveryClient
@EnableTransactionManagement
@MapperScan(basePackages  = {"com.zainzhou.dao.mapper"})
@SpringBootApplication
public class CustomizeApplication implements CommandLineRunner {

    @Value("${zainzhou.env:}")
    private String env;

    public static void main(String[] args) {
        SpringApplication.run(CustomizeApplication.class);
    }

    @Override
    public void run(String... args) {
        log.info("----------------------------------------------------------------------");
        log.info("CustomizeApplication start Ok！");
        log.info("env:{}", env);
        log.info("----------------------------------------------------------------------");
    }
}
