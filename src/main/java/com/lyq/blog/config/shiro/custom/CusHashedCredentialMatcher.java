package com.lyq.blog.config.shiro.custom;

import com.lyq.blog.constants.Constant;
import com.lyq.blog.exception.BusinessException;
import com.lyq.blog.service.RedisService;
import com.lyq.blog.utils.jwt.JwtTokenUtil;
import com.lyq.blog.utils.result.code.ResponseCode;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

/**
 * 第三步
 * Shiro认证匹配重写
 *
 * @Author: 林义清
 * @Date: 2020/4/24 12:42 上午
 * @Version: 1.0.0
 */
public class CusHashedCredentialMatcher extends HashedCredentialsMatcher {
    @Autowired
    private RedisService redisService;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        CusUsernamePasswordToken cusUsernamePasswordToken = (CusUsernamePasswordToken) token;
        // 获取token 从自定义 CusUsernamePasswordToken 获取jwt token主体
        String accessToken = (String) cusUsernamePasswordToken.getPrincipal();
        //从token中取出userId
        String userId = JwtTokenUtil.getUserId(accessToken);
        //因为我删除用户 之后token 需要被弃用 所以采用redis进行过期处理
        // 1. 判断用户是否被删除 如果redis里面存在这个key 就抛出异常信息
        if (redisService.hasKey(Constant.DELETED_USER_KEY + userId)) {
            throw new BusinessException(ResponseCode.ACCOUNT_HAS_BEEN_DELETED);
        }
        // 2. 判断用户是否是主动退出登录 如果redis里面存在这个key 就抛出异常信息
        if (redisService.hasKey(Constant.JWT_ACCESS_TOKEN_BLACKLIST + accessToken)) {
            throw new BusinessException(ResponseCode.TOKEN_ERROR);
        }
        // 3. 判断accessToken 是否通过校验
        if (!JwtTokenUtil.validateToken(accessToken)) {
            throw new BusinessException(ResponseCode.TOKEN_PAST_DUE);
        }
        // 4. 判断用户是否是主动去刷新 如果key = Constant.JWT_REFRESH_KEY + userId 大于 accessToken 说明
        // accessToken 不是重新生成的 这样就要判断它是否刷新过了/或者是否是新生成的 token
        if (redisService.hasKey(Constant.JWT_REFRESH_KEY + userId)) {
            if (redisService.getExpire(Constant.JWT_REFRESH_KEY + userId, TimeUnit.MILLISECONDS) >
                    JwtTokenUtil.getRemainingTime(accessToken)) {
                // 判断这个登录用户是否主动去刷新  通过剩余的过期时间比较
                // 如果 (token的剩余时间) > (标记的key的剩余过期时间) 就说明token是在这个标记key之后生成的
                throw new BusinessException(ResponseCode.TOKEN_PAST_DUE);
            }
        }
        // 都符合要求 就放行
        return true;
    }
}
