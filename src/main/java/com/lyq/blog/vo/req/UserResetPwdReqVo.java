package com.lyq.blog.vo.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用户重设密码vo
 *
 * @Author: 林义清
 * @Date: 2020/4/25 4:27 下午
 * @Version: 1.0.0
 */
@Data
public class UserResetPwdReqVo {
    @NotBlank(message = "旧密码不能为空")
    private String oldPassword;
    @NotBlank(message = "新密码不能为空")
    private String newPassword;
}
