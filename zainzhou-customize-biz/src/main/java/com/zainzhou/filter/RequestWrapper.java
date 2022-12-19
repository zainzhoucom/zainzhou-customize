package com.zainzhou.filter;

import com.zainzhou.utils.CommonUtil;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author : 周振宇
 * Created on 2022/10/26 15:08
 **/
public class RequestWrapper extends HttpServletRequestWrapper {

    private final byte[] body;
    private final HashMap<String,String> headMap;
    private final HashMap<String,String> requestParamMap;

    public RequestWrapper(HttpServletRequest request) {
        super(request);

        body = CommonUtil.getBodyString(request).getBytes(Charset.forName("UTF-8"));

        headMap = new HashMap();
        Enumeration<String> headNameList = request.getHeaderNames();
        while (headNameList.hasMoreElements()){
            String key = headNameList.nextElement();
            headMap.put(key.toLowerCase(),request.getHeader(key));
        }

        requestParamMap = new HashMap<>();
        Enumeration<String> parameterNameList = request.getParameterNames();
        while (parameterNameList.hasMoreElements()){
            String key = parameterNameList.nextElement();
            requestParamMap.put(key,request.getParameter(key));
        }
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {

        final ByteArrayInputStream bais = new ByteArrayInputStream(body);

        return new ServletInputStream() {

            @Override
            public int read() throws IOException {
                return bais.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }
        };
    }

    @Override
    public String getHeader(String name) {
        return headMap.get(name.toLowerCase());
    }

    @Override
    public String getParameter(String name) {
        return requestParamMap.get(name);
    }
}
