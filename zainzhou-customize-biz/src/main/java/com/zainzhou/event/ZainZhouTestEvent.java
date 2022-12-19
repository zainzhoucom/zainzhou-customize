package com.zainzhou.event;

import com.zainzhou.event.model.AsyncTransactionModel;
import org.springframework.context.ApplicationEvent;

/**
 * @author : 周振宇
 * Created on 2022/10/28 11:10
 **/
public class ZainZhouTestEvent extends ApplicationEvent {

    private static final long serialVersionUID = 6719755332538958003L;

    public ZainZhouTestEvent(AsyncTransactionModel source) {
        super(source);
    }
}
