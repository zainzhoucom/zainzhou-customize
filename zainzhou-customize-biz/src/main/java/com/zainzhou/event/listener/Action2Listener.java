package com.zainzhou.event.listener;

import com.zainzhou.event.Action2Event;
import com.zainzhou.event.model.AsyncTransactionModel;
import com.zainzhou.handler.AsyncTransactionalHandler;
import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author : 周振宇
 * Created on 2022/11/01 10:07
 **/
@Component
public class Action2Listener implements ApplicationListener<Action2Event> {

    @Resource
    private AsyncTransactionalHandler asyncTransactionalHandler;

    @Override
    public void onApplicationEvent(@NonNull Action2Event action2Event) {
        AsyncTransactionModel model = (AsyncTransactionModel)action2Event.getSource();
        asyncTransactionalHandler.createTestRecord(model);
    }
}
