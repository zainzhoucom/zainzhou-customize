package com.zainzhou.utils;

/**
 * @author : 周振宇
 * Created on 2022/11/17 17:55
 **/
public class TransactionalUtils {
    private TransactionalUtils(){}

    private static TransactionalComponent transactionalComponent;

    private static synchronized TransactionalComponent getTransactionalComponent(){
        if(null == transactionalComponent){
            transactionalComponent = ApplicationContextUtils.getBean(TransactionalComponent.class);
        }
        return transactionalComponent;
    }

    public static void required(TransactionalComponent.Cell cell) throws Exception{
        getTransactionalComponent().required(cell);
    }

    public static void requiredNew(TransactionalComponent.Cell cell) throws Exception{
        getTransactionalComponent().requiredNew(cell);
    }
}
