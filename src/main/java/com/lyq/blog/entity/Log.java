package com.lyq.blog.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 日志表
 *
 * @Author: 林义清
 * @Date: 2020/4/24 2:07 上午
 * @Version: 1.0.0
 */
@Data
@TableName(value = "bl_log")
public class Log implements Serializable {
    /**
     * 日志id
     */
    @TableId(value = "log_id", type = IdType.AUTO)
    private Long logId;

    /**
     * 请求路由
     */
    @TableField(value = "log_url")
    private String logUrl;

    /**
     * 请求参数
     */
    @TableField(value = "log_params")
    private String logParams;

    /**
     * 访问状态 1:正常 0;异常
     */
    @TableField(value = "log_status")
    private Integer logStatus;

    /**
     * 异常信息
     */
    @TableField(value = "log_message")
    private String logMessage;

    /**
     * 请求方式 GET POST PUT DELETE
     */
    @TableField(value = "log_method")
    private String logMethod;

    /**
     * 响应时间
     */
    @TableField(value = "log_time")
    private Long logTime;

    /**
     * 响应返回信息
     */
    @TableField(value = "log_result")
    private String logResult;

    /**
     * 请求人 ip地址
     */
    @TableField(value = "log_ip")
    private String logIp;

    /**
     * 创建时间
     */
    @TableField(value = "create_at", fill = FieldFill.INSERT)
    private Date createAt;

    /**
     * 创建人id
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    private static final long serialVersionUID = 1L;

    public static final String COL_LOG_ID = "log_id";

    public static final String COL_LOG_URL = "log_url";

    public static final String COL_LOG_PARAMS = "log_params";

    public static final String COL_LOG_STATUS = "log_status";

    public static final String COL_LOG_MESSAGE = "log_message";

    public static final String COL_LOG_METHOD = "log_method";

    public static final String COL_LOG_TIME = "log_time";

    public static final String COL_LOG_RESULT = "log_result";

    public static final String COL_LOG_IP = "log_ip";

    public static final String COL_CREATE_AT = "create_at";

    public static final String COL_CREATE_BY = "create_by";
}