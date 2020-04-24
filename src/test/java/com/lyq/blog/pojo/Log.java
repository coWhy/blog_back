package com.lyq.blog.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @Author: 林义清
 * @Date: 2020/4/24 1:43 上午
 * @Version: 1.0.0
 */
@Data
public class Log {
    private Long logId; // 日志id
    private String logUrl;// 请求路由
    private String logParams;//请求参数
    private Integer logStatus;// 访问状态 1:正常 0;异常
    private String logMessage;// 异常信息
    private String log_method;// 请求方式 GET POST PUT DELETE
    private Long logTime;// 响应时间
    private String logResult;// 响应返回信息
    private String logIp;// 请求人 ip地址
    private Date createAt; // 创建时间
    private String createBy;//创建人id
}
