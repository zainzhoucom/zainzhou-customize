package com.zainzhou.controller;

import com.zainzhou.handler.RequestHandler;
import com.zainzhou.utils.Result;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : 周振宇
 * Created on 2022/10/26 11:01
 **/
@RestController
@RequestMapping("/zainzhou/request/")
public class RequestDemoController {

    @Resource
    private RequestHandler requestService;


    @GetMapping("query-header")
    public Result<String> testQueryHeader(){
        requestService.queryHeaderUser();
        return Result.success();
    }
}
