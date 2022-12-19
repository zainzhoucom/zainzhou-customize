package com.zainzhou.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author : 周振宇
 * Created on 2022/10/26 11:05
 **/
@Slf4j
public class RequestUtil {


    private RequestUtil() {
    }

    private static final String ENC = "UTF-8";

    /**
     * 获取请求对象
     *
     * @return {@link HttpServletRequest}
     */
    public static HttpServletRequest getRequest() throws Exception {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        if (servletRequestAttributes == null) {
            throw new Exception("未取到Request信息");
        }
        return servletRequestAttributes.getRequest();
    }

    /**
     * 获取登录用户信息
     *
     * @return {@link RequestUserInfo}
     */
    public static RequestUserInfo getUserInfo() {
        RequestUserInfo userInfo = new RequestUserInfo();
        try {
            HttpServletRequest request = CommonUtil.getRequest();
            userInfo = RequestUserInfo.builder().userCode(request.getHeader("userCode"))
                    .userName(request.getHeader("userName"))
                    .build();

            userInfo.setUserName(URLDecoder.decode(userInfo.getUserName(), ENC));
        } catch (Exception e) {
            log.info("读取用户名出错");
        }
        if(StringUtils.isBlank(userInfo.getUserCode())){
            userInfo.setUserCode("undefined_code");
        }
        if(StringUtils.isBlank(userInfo.getUserName())){
            userInfo.setUserName("undefined_name");
        }

        return userInfo;
    }
}
