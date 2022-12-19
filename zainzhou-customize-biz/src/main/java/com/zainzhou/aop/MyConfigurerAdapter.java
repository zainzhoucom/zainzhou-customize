package com.zainzhou.aop;

import com.zainzhou.socket.ZainTestSocketHandler;
import javax.annotation.Resource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author : 周振宇
 * Created on 2022/12/13 14:38
 **/
@EnableWebSocket
@EnableWebMvc
public class MyConfigurerAdapter implements WebSocketConfigurer {

    @Resource
    private ZainTestSocketHandler zainTestSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(zainTestSocketHandler,"")
                .setAllowedOrigins("*").addInterceptors(new HandshakeInterceptor());
    }
}
