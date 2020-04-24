package com.lyq.blog.config.shiro.custom;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.lyq.blog.constants.Constant;
import com.lyq.blog.exception.BusinessException;
import com.lyq.blog.utils.result.CommonResult;
import com.lyq.blog.utils.result.code.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * 第二步
 * 拦截认证的请求
 * 验证客户端header是否携带token:
 * 是->放开 进行SecurityManager验证
 * 否->进行拦截 引导客户端到登录页面进行重新登录
 *
 * @Author: 林义清
 * @Date: 2020/4/24 12:26 上午
 * @Version: 1.0.0
 */
@Slf4j
public class CusAccessControlFilter extends AccessControlFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        log.info("请求方式：{}", req.getMethod());
        log.info("拦截请求URI: {}", req.getRequestURI());
        //1.验证用户是否携带token
        String accessToken = req.getHeader(Constant.ACCESS_TOKEN);
        try {
            if (StringUtils.isBlank(accessToken)) {
                //如果前端没有携带token 就抛出自定义全局异常信息
                throw new BusinessException(ResponseCode.TOKEN_NOT_NULL);
            }
            // 进行shiro的认证匹配 并且采用自定jwt token
            CusUsernamePasswordToken cusUsernamePasswordToken = new CusUsernamePasswordToken(accessToken);
            getSubject(request, response).login(cusUsernamePasswordToken);
        } catch (BusinessException e) { // 如果 认证匹配 用户不匹配 就抛出自定义异常
            // 引导客户端到登录页面进行重新登录 采用自定义响应结果集 返回用户异常信息
            customResponse(e.getCode(), e.getDefaultMessage(), response);
            // 不放行 进行拦截
            return false;
        } catch (AuthenticationException e) {
            // 进行shiro的认证匹配异常拦截 拦截前 再次进行异常判断是否是业务异常
            if (e.getCause() instanceof BusinessException) {
                BusinessException exception = (BusinessException) e.getCause();
                customResponse(exception.getCode(), exception.getDefaultMessage(), response);
            } else {
                customResponse(ResponseCode.SHIRO_AUTHORIZATION_ERROR.getCode(),
                        ResponseCode.SHIRO_AUTHORIZATION_ERROR.getMsg(), response);

            }
            // 不放行 进行拦截
            return false;
        }
        // 以上全部满足要求 就放行 返回正常的响应信息
        return true;
    }

    /**
     * 自定义错误响应
     * 自定义异常的类，用户返回给客户端的相应的json格式的信息
     *
     * @param code     错误代码
     * @param msg      错误信息
     * @param response 响应
     */
    public void customResponse(int code, String msg, ServletResponse response) {
        try {
            CommonResult result = CommonResult.getResult(code, msg);
            response.setContentType("application/json;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            String errorJson = JSON.toJSONString(result);
            OutputStream out = response.getOutputStream();
            out.write(errorJson.getBytes(StandardCharsets.UTF_8));
            out.flush();
        } catch (IOException e) {
            log.info("错误信息：{}", e.getLocalizedMessage());
        }
    }
}
