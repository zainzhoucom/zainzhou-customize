package com.zainzhou.utils;

import com.alibaba.ttl.TransmittableThreadLocal;
import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author : 周振宇
 * Created on 2022/10/26 14:54
 **/
public class CommonUtil {

    private CommonUtil(){}

    private static final TransmittableThreadLocal<HttpServletRequest> REQUEST_TRANSMITTABLE_THREAD_LOCAL = new TransmittableThreadLocal<>();

    public static void shareRequest(HttpServletRequest request){
        REQUEST_TRANSMITTABLE_THREAD_LOCAL.set(request);
    }

    public static HttpServletRequest getRequest(){
        HttpServletRequest request = REQUEST_TRANSMITTABLE_THREAD_LOCAL.get();
        if(request!=null){
            return REQUEST_TRANSMITTABLE_THREAD_LOCAL.get();
        }else{
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if(requestAttributes!=null){
                return  requestAttributes.getRequest();
            }else{
                return  null;
            }
        }
    }

    public static void remove(){
        REQUEST_TRANSMITTABLE_THREAD_LOCAL.remove();
    }

    public static String getBodyString(HttpServletRequest request) {
        StringBuffer wholeStr = new StringBuffer();
        try {
            String str;
            BufferedReader br = request.getReader();
            while((str = br.readLine()) != null){
                wholeStr.append(str);
            }
        }catch (IOException e){

        }

        return wholeStr.toString();
    }
}
