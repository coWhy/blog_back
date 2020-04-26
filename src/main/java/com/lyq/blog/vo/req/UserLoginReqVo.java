package com.lyq.blog.vo.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用户登录vo
 *
 * @Author: 林义清
 * @Date: 2020/4/24 2:26 上午
 * @Version: 1.0.0
 */
@Data
public class UserLoginReqVo {
    @NotBlank(message = "账号不能为空")
    private String username;
    @NotBlank(message = "登录密码不能为空")
    private String password;
}
