package com.zainzhou.config;

import com.zainzhou.utils.CommonUtil;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskDecorator;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author : 周振宇
 * Created on 2022/10/26 15:14
 **/
@Slf4j
public class CustomTaskDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(Runnable runnable) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        log.info("异步任务共享request");
        return () -> {
            try {
                CommonUtil.shareRequest(request);
                runnable.run();
            } finally {
                CommonUtil.remove();
            }
        };
    }
}
