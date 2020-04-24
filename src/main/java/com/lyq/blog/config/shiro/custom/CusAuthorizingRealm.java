package com.lyq.blog.config.shiro.custom;

import com.lyq.blog.constants.Constant;
import com.lyq.blog.constants.StateEnums;
import com.lyq.blog.utils.jwt.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Objects;

/**
 * 第四步
 * 自定义realm
 *
 * @Author: 林义清
 * @Date: 2020/4/24 1:18 上午
 * @Version: 1.0.0
 */
@Slf4j
public class CusAuthorizingRealm extends AuthorizingRealm {
    /**
     * 支持自定义token
     *
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof CusUsernamePasswordToken;
    }

    /**
     * 这里用来处理 对于普通用户 和 管理员 的权限控制
     * 加上 @RequiresPermissions() 注解的 只有管理员才能访问 其他的将会被全局拦截
     * 所以在登录的时候 jwt token中的claims 中需要 假如key  JWT_IS_ADMIN 来进行shiro对权限的简单处理
     *
     * @param principals
     * @param permission
     * @return
     */
    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        //取出token
        String token = (String) principals.getPrimaryPrincipal();
        String userId = JwtTokenUtil.getUserId(token);
        Claims claims = JwtTokenUtil.getClaimsFromToken(token);
        Integer isAdmin = claims.get(Constant.JWT_IS_ADMIN, Integer.class);
        log.info("当前操作的用户id是:{},用户身份:{}", userId, isAdmin == 1 ? "管理员" : "普通用户");
        return Objects.equals(isAdmin, StateEnums.ADMIN.getCode()) || super.isPermitted(principals, permission);
    }

    /**
     * 主要用于用户授权
     * 一般分配用户权限
     * 由于是个人博客 所以只有一个管理员
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return new SimpleAuthorizationInfo();
    }

    /**
     * 主要用于 用户的认证
     * 以前是验证用户密码
     * 这里改造成验证 jwt token
     * 一般来说客户端 只需要登录
     * 一次登录 后续的访问用token来维护用户的登陆状态
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        CusUsernamePasswordToken cusUsernamePasswordToken = (CusUsernamePasswordToken) token;
        return new SimpleAuthenticationInfo(
                // token主体
                cusUsernamePasswordToken.getPrincipal(),
                cusUsernamePasswordToken.getCredentials(),
                this.getName()
        );
    }
}
