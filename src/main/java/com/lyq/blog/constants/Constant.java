package com.lyq.blog.constants;

/**
 * 常量
 *
 * @version 1.0
 * @author: 林义清
 * @date: 2020/4/18 3:43 上午
 */
public class Constant {
    /**
     * 用户名key
     */
    public static final String JWT_USER_NAME = "jwt_user_name_key_";
    /**
     * 是否是管理员
     */
    public static final String JWT_IS_ADMIN = "jwt_is_admin_";
    /**
     * 正常token 登录时候产生
     */
    public static final String ACCESS_TOKEN = "Authorization";
    /**
     * 刷新 token 用户修改密码等关键信息时候 刷新
     * 就是 退出了 或者 关键字段修改了 就拉入黑名单 然后判断是否在黑名单里面 再进行刷新
     */
    public static final String REFRESH_TOKEN = "RefreshToken";
    /**
     * 主动退出后加入黑名单 key access_token
     */
    public static final String JWT_ACCESS_TOKEN_BLACKLIST = "jwt_access_token-blacklist_";
    /**
     * 主动退出后加入黑名单 key refresh_token
     */
    public static final String JWT_REFRESH_TOKEN_BLACKLIST = "jwt_refresh_token_blacklist_";
    /**
     * 标记用户 是否被删除
     */
    public static final String DELETED_USER_KEY = "deleted_user_key_";
    public static final String JWT_REFRESH_KEY = "jwt_refresh_key_";
    /**
     * 用户重置密码
     */
    public static final String USER_RESET_PWD = "123456";
}
