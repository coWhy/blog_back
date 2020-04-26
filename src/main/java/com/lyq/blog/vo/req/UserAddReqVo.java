package com.lyq.blog.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 管理员添加新用户
 *
 * @Author: 林义清
 * @Date: 2020/4/24 3:12 上午
 * @Version: 1.0.0
 */
@Data
public class UserAddReqVo {
    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "登录密码不能为空")
    private String password;

    @NotBlank(message = "头像不能为空")
    private String avatar;

    @NotNull(message = "性别不能为空")
    private Integer sex;

    @NotBlank(message = "昵称不能为空")
    private String nickName;

    @NotBlank(message = "手机号码不能为空")
    private String phone;

    @NotBlank(message = "邮箱地址不能为空")
    private String email;
}
