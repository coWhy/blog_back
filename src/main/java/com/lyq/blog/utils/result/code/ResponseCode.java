package com.lyq.blog.utils.result.code;

/**
 * 枚举类
 *
 * @version 1.0
 * @author: 林义清
 * @date: 2020/4/18 1:12 上午
 */
public enum ResponseCode implements ResponseCodeInterface {
    /**
     * code=0：服务器已成功处理了请求。 通常，这表示服务器提供了请求的网页。
     * code=4010001：（授权异常） 请求要求身份验证。 客户端需要跳转到登录页面重新登录
     * code=4010002：(凭证过期) 客户端请求刷新凭证接口
     * code=4030001：没有权限禁止访问
     * code=400xxxx：系统主动抛出的业务异常
     * code=5000001：系统异常
     */
    SUCCESS(0, "操作成功"),
    UPDATE_SUCCESS(0, "更新成功"),
    ADD_SUCCESS(0, "添加成功"),
    DELETE_SUCCESS(0, "删除成功"),
    REGISTER_SUCCESS(0, "注册成功"),
    LOGIN_SUCCESS(0, "登录成功"),
    LOGOUT_SUCCESS(0, "登出成功"),
    ENABLE_SUCCESS(0, "启用成功"),
    DISABLE_SUCCESS(0, "弃用成功"),
    QUERY_SUCCESS(0, "查询成功"),
    RESET_PWD_SUCCESS(0, "重置密码成功"),
    MODIFY_PWD_SUCCESS(0, "修改密码成功"),
    REFRESH_TOKEN_SUCCESS(0, "刷新token成功"),
    SYSTEM_ERROR(5000001, "系统异常请稍后再试"),
    DATA_INCOMING_ERROR(4000001, "数据传入异常"),
    DATA_CHECK_ANOMALY(4000002, "数据校验异常"),
    QUERY_DATA_ERROR(4000003, "查询不到您所需要的信息"),
    ACCOUNT_ERROR(4000003, "该账号不存在"),
    ACCOUNT_PASSWORD_ERROR(4000003, "用户名密码不匹配"),

    TOKEN_NOT_NULL(4010001, "token不能为空"),
    SHIRO_AUTHORIZATION_ERROR(4010001, "用户认证异常"),
    TOKEN_ERROR(4010001, "用户未登录，请重新登录"),
    ACCOUNT_HAS_BEEN_DELETED(4010001, "该账号已被注销,请联系管理员"),
    ACCOUNT_IS_NOT_ADMIN(4010001, "该用户不是管理员,没有权限访问该资源"),
    NOT_DELETE_ADMIN(4010001, "管理员不能删除"),
    ACCOUNT_HAS_NOT_PERMISSIONS(4010001, "您不是管理员,不能访问该资源"),
    USELESS_OPERATION(4010001, "当前操作对象不存在"),

    TOKEN_PAST_DUE(4010002, "token失效，请刷新token"),
    OPERATION_ERROR(4000005, "操作失败"),

    /**
     * 博客分类异常处理
     */
    BLOG_TYPE_HAS_REPEATED(2100000, "该分类名称已存在,请换一个试试"),
    BLOG_TYPE_HAS_ENABLED(2100001, "该分类名称已启用,请不要重复操作"),
    BLOG_TYPE_HAS_DISABLED(2100002, "该分类名称已弃用,请不要重复操作"),
    /**
     * 友情链接异常处理
     */
    BLOG_LINK_NAME_HAS_REPEATED(2200000, "该链接名称已存在,请换一个试试"),
    /**
     * 用户异常相关处理
     */
    ACCOUNT_HAS_BEEN_REGISTERD(2300000, "用户名已存在,请换一个试试"),
    ;


    private int code;

    private String msg;


    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
