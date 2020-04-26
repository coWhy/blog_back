package com.lyq.blog.vo.resp;

import lombok.Getter;

/**
 * 用户登录后响应返回值
 *
 * @Author: 林义清
 * @Date: 2020/4/24 2:26 上午
 * @Version: 1.0.0
 */
@Getter
public class UserLoginRespVo {
    /**
     * 正常的业务token 就是Authorization 的 header载体
     */
    private String accessToken;

    /**
     * 刷新token
     */
    private String refreshToken;
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 是否是管理员
     */
    private Integer isAdmin;

    public UserLoginRespVo setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public UserLoginRespVo setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public UserLoginRespVo setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserLoginRespVo setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
        return this;
    }

    public UserLoginRespVo setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

}
