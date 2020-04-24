package com.lyq.blog.exception;


import com.lyq.blog.utils.result.CommonResult;
import com.lyq.blog.utils.result.code.ResponseCode;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 全局异常统一处理类
 *
 * @version 1.0
 * @author: 林义清
 * @date: 2020/4/18 1:12 上午
 */
@RestControllerAdvice
public class RestExceptionHandler {
    /**
     * 全局统一异常
     *
     * @param e
     * @param <T>
     * @return
     */
    @ExceptionHandler(Exception.class)
    public <T> CommonResult<T> handleException(Exception e) {
        System.err.println("系统异常信息(Exception):" + e.getLocalizedMessage());
        //系统异常请稍后再试
        return CommonResult.getResult(ResponseCode.SYSTEM_ERROR);
    }


    @ExceptionHandler(AuthorizationException.class)
    public CommonResult authorizationException(AuthorizationException e) {
        System.err.println("用户权限异常(BusinessException):" + e.getLocalizedMessage());
        return CommonResult.getResult(ResponseCode.ACCOUNT_HAS_NOT_PERMISSIONS);
    }


    /**
     * 全局捕获自定义业务异常 BusinessException
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public CommonResult businessException(BusinessException e) {
        System.err.println("业务异常信息(BusinessException):" + e.getLocalizedMessage());
        return CommonResult.getResult(e.getCode(), e.getDefaultMessage());
    }

    /**
     * 处理validation框架异常,全局捕获校验抛出异常 MethodArgumentNotValidException
     *
     * @param e
     * @param <T>
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public <T> CommonResult<T> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        System.err.println("validation框架异常信息(MethodArgumentNotValidException):" + e.getBindingResult().getAllErrors());
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        String[] infos = new String[errors.size()];
        int i = 0;
        for (ObjectError error : errors) {
            infos[i] = error.getDefaultMessage();
            System.err.println("信息: " + infos[i]);
            i++;
        }
        return CommonResult.getResult(ResponseCode.DATA_CHECK_ANOMALY.getCode(), infos[0]);
    }

}
