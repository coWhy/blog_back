package com.lyq.blog.constants;

import lombok.Getter;

/**
 * @version 1.0
 * @author: 林义清
 * @date: 2020/4/18 4:19 上午
 */
@Getter
public enum StateEnums {
    /**
     * 逻辑删除状态
     */
    DELETED(1, "已删除"),
    NOT_DELETED(0, "未删除"),

    /**
     * 启用状态
     */
    ENABLED(1, "已启用"),
    DISABLED(0, "已弃用"),

    /**
     * 性别状态
     */
    GENDER_MAN(1, "男"),
    GENDER_WOMAN(0, "女"),

    /**
     * 请求访问状态枚举
     */
    REQUEST_SUCCESS(1, "请求正常"),
    REQUEST_ERROR(0, "请求异常"),

    /**
     * 用户标识。
     * 1表示管理员，0表示普通用户
     */
    ADMIN(1, "管理员"),
    USER(0, "普通用户"),

    SYSTEM(1, "系统id"),
    ;

    private final Integer code;
    private final String msg;

    StateEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}