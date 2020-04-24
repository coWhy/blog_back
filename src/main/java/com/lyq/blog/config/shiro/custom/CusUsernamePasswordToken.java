package com.lyq.blog.config.shiro.custom;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 第一步
 * 将shiro的认证token设置为jwt的token
 *
 * @Author: 林义清
 * @Date: 2020/4/24 12:16 上午
 * @Version: 1.0.0
 */
public class CusUsernamePasswordToken extends UsernamePasswordToken {
    /**
     * token
     */
    private String token;

    public CusUsernamePasswordToken(String token) {
        this.token = token;
    }

    /**
     * 这是token主体 将jwt token 对shiro的原有token进行覆盖
     *
     * @return
     */
    @Override
    public Object getPrincipal() {
        return token;
    }
}
