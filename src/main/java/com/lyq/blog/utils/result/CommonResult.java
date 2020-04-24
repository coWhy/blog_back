package com.lyq.blog.utils.result;


import com.lyq.blog.utils.result.code.ResponseCode;
import com.lyq.blog.utils.result.code.ResponseCodeInterface;
import lombok.Data;

/**
 * @version 1.0
 * @author: 林义清
 * @date: 2020/4/17 9:46 下午
 */
@Data
public class CommonResult<T> {
    /**
     * code 请求响应code， 0表示请求成功 其它表示失败
     * msg 响应客户端的提示
     * data 响应客户端内容
     */
    private int code;
    private String msg;
    private T data;

    public CommonResult(int code, T data) {
        this.code = code;
        this.data = data;
        this.msg = null;
    }

    public CommonResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = null;
    }

    public CommonResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public CommonResult() {
        this.code = ResponseCode.SUCCESS.getCode();
        this.msg = ResponseCode.SUCCESS.getMsg();
        this.data = null;
    }

    public CommonResult(T data) {
        this.data = data;
        this.code = ResponseCode.SUCCESS.getCode();
        this.msg = ResponseCode.SUCCESS.getMsg();
    }

    public CommonResult(ResponseCodeInterface responseCodeInterface) {
        this.data = null;
        this.code = responseCodeInterface.getCode();
        this.msg = responseCodeInterface.getMsg();
    }

    public CommonResult(ResponseCodeInterface responseCodeInterface, T data) {
        this.data = data;
        this.code = responseCodeInterface.getCode();
        this.msg = responseCodeInterface.getMsg();
    }

    /**
     * 操作成功 data为null
     */
    public static CommonResult success() {
        return new CommonResult();
    }

    /**
     * 操作成功 data 不为null
     */
    public static <T> CommonResult success(T data) {
        return new CommonResult(data);
    }

    /**
     * 自定义 返回操作 data 可控
     */
    public static <T> CommonResult getResult(int code, String msg, T data) {
        return new CommonResult(code, msg, data);
    }

    /**
     * 自定义返回  data为null
     */
    public static CommonResult getResult(int code, String msg) {
        return new CommonResult(code, msg);
    }

    /**
     * 自定义返回 入参一般是异常code枚举 data为空
     */
    public static CommonResult getResult(ResponseCode responseCode) {
        return new CommonResult(responseCode);
    }


    /**
     * 自定义返回 入参一般是异常code枚举 data 可控
     */
    public static <T> CommonResult getResult(ResponseCode responseCode, T data) {

        return new CommonResult(responseCode, data);
    }
}
