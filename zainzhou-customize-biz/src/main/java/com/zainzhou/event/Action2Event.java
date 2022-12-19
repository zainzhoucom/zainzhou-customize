package com.zainzhou.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author : 周振宇
 * Created on 2022/11/01 10:06
 **/
public class Action2Event extends ApplicationEvent {

    private static final long serialVersionUID = -8943029095262443049L;

    public Action2Event(Object source) {
        super(source);
    }
}
