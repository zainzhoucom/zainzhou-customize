package com.zainzhou.aspect;

import com.zainzhou.utils.Result;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

/**
 * @author : 周振宇
 * Created on 2022/09/29 15:07
 **/
@RestControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionAdvice {
    @ExceptionHandler({ConstraintViolationException.class,
            MethodArgumentNotValidException.class,
            BindException.class,
            MissingServletRequestParameterException.class,
            MissingServletRequestPartException.class,
            MethodArgumentNotValidException.class,
            MethodArgumentTypeMismatchException.class})
    public Result<Object> validationBodyException(Exception exception) {
        log.warn("参数校验失败", exception);
        return buildFailResult("参数校验失败，请确认后重试", "99999999");
    }

    private Result<Object> buildFailResult(String message, String errorCode) {
        Result<Object> result = new Result<>();
        result.setIsSuccess(false);
        result.setData(null);
        result.setMsg(message);
        result.setCode(errorCode);
        return result;
    }
}
