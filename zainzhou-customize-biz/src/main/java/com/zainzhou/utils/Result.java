package com.zainzhou.utils;

import java.util.function.Consumer;
import java.util.function.Function;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : 周振宇
 * Created on 2022/09/29 13:39
 **/
@Getter
@Setter
public class Result<T> {

    public static final String SUCCESS_CODE = "00000000";
    /**
     * 无论 SUCCESS或者FAIL都需要返回一个code
     */
    private String code;

    /**
     * 如果异常，则需要返回异常msg
     */
    private String msg;

    /**
     * 加这个标识，主要是考虑，前后端交互，以及后端各个微服务交互， 如果isSuccess为true，代表我的请求是成功的； 如果isSuccess是false，代表我的请求是失败的
     */
    private Boolean isSuccess;

    /**
     * 如果成功，需要返回对应的Response
     */
    private T data;


    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setCode(SUCCESS_CODE);
        result.setMsg("success");
        result.setIsSuccess(Boolean.TRUE);
        result.setData(null);
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(SUCCESS_CODE);
        result.setMsg("success");
        result.setIsSuccess(Boolean.TRUE);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> failOfCode(String code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setIsSuccess(Boolean.FALSE);
        return result;
    }

    /**
     * 用于安全的拆包 当success 为true时可以获取正确的data对象[T] 当success 为false时 1.result.open(null) 则只返回null对象，不会抛出异常 2.result.open(p ->
     * { throw new BusinessException(code, msg); }) 抛出 XXXException
     *
     * @param failedConsumer 异常时，消费方法
     * @return {@link T}
     */
    public T open(Consumer<Result<T>> failedConsumer) {
        if (SUCCESS_CODE.equals(this.code)) {
            return this.getData();
        }
        if (failedConsumer != null) {
            failedConsumer.accept(this);
        }
        return null;
    }

    /**
     * 用于成功处理逻辑
     *
     * @param consumer 成功消费方法
     * @return {@link Result<T>}
     */
    public Result<T> then(Consumer<T> consumer) {
        if (SUCCESS_CODE.equals(this.code)) {
            consumer.accept(this.data);
        }
        return this;
    }

    /**
     * 用于转换处理逻辑
     *
     * @param mapper 成功转换
     * @return {@link Result<T>}
     */
    public <R> Result<R> map(Function<T, R> mapper) {
        Result<R> result = new Result<>();
        result.setCode(this.code);
        result.setMsg(this.msg);
        result.setIsSuccess(this.isSuccess);
        if (this.data != null) {
            result.setData(mapper.apply(this.data));
        }
        return result;
    }
}
