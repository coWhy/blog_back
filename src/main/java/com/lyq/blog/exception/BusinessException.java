package com.lyq.blog.exception;


import com.lyq.blog.utils.result.code.ResponseCode;

/**
 * 业务异常类
 * 根据相应的业务
 * 抛出相应的运行时异常，进行数据库事务回滚，
 * 并希望该异常信息能被返回显示给用户
 *
 * @version 1.0
 * @author: 林义清
 * @date: 2020/4/18 1:12 上午
 */
public class BusinessException extends RuntimeException {
    /**
     * code 异常
     * defaultMessage 异常提示
     */
    private final int code;
    public final String defaultMessage;

    public BusinessException(int code, String defaultMessage) {
        super(defaultMessage);
        this.code = code;
        this.defaultMessage = defaultMessage;
    }

    public BusinessException(ResponseCode responseCode) {
        this(responseCode.getCode(), responseCode.getMsg());
    }

    public int getCode() {
        return code;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}
