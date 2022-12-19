package com.zainzhou.controller;

import com.zainzhou.reqeust.AddCacheRequest;
import com.zainzhou.utils.RedisService;
import com.zainzhou.utils.Result;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : 周振宇
 * Created on 2022/09/29 13:20
 **/
@RestController
@RequestMapping("/zainzhou/redis/")
public class RedisDemoController {

    @Resource
    private RedisService redisService;

    @PostMapping("cache-set")
    public Result<String> testSetCache(@RequestBody @Validated AddCacheRequest request){
        redisService.addCache(request.getKey(), request.getValue(), request.getTimeOut(), TimeUnit.SECONDS);
        return Result.success("set success");
    }

    @GetMapping("cache-get")
    public Result<String> testGetCache(@RequestParam(name = "key")String key){
        return Result.success((String) redisService.getCache(key));
    }
}
