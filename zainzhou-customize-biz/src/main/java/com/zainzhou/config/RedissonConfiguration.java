package com.zainzhou.config;

import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : 周振宇
 * Created on 2022/09/29 14:45
 **/
@Slf4j
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
public class RedissonConfiguration {

    private String host;
    private String port;
    private String password;
    private final Cluster cluster = new Cluster();

    private static  final String CLUSTER_PATTERN = "redis://%s";
    private static  final String SINGLE_PATTERN = "redis://%s:%s";


    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public class Cluster{
        private String nodes;
    }

    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        String nodes = cluster.getNodes();
        if(StringUtils.isNotBlank(nodes)){
            //集群配置
            log.info("redis cluster servers");
            config.useClusterServers()
                    .setPassword(password)
                    .setScanInterval(2000)
                    .setCheckSlotsCoverage(false)
                    .addNodeAddress(Stream.of(nodes.split(",")).map(item -> String.format(CLUSTER_PATTERN, item))
                            .toArray(String[]::new));
            return Redisson.create(config);
        }
        log.info("redis single server");
        config.useSingleServer()
                .setPassword(password)
                .setAddress(String.format(SINGLE_PATTERN,host,port));
        return Redisson.create(config);
    }
}
