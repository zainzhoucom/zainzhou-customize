package com.zainzhou.utils;

import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author : 周振宇
 * Created on 2022/09/29 13:23
 **/
@Slf4j
@Service
public class RedisService {

    @Resource
    private RedisTemplate redisTemplate;

    @Value("${spring.application.name}")
    private String prefix;

    private String buildKey(String key){
        return String.format("%s:%s", prefix, key);
    }

    public void addCache(String key, String value, long timeout, TimeUnit timeUnit){
        redisTemplate.opsForValue().set(buildKey(key), value, timeout, timeUnit);
    }

    public Object getCache(String key){
        return redisTemplate.opsForValue().get(buildKey(key));
    }

    public Long incr(String key, Long delta) {
        if (delta < 0L) {
            throw new RuntimeException("递增因子必须大于0");
        } else {
            return this.redisTemplate.opsForValue().increment(buildKey(key), delta);
        }
    }

}
