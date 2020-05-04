package com.lyq.blog.vo.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 管理员更新个人信息
 *
 * @Author: 林义清
 * @Date: 2020/5/4 11:49 上午
 * @Version: 1.0.0
 */
@Data
public class AdminUpdateVo {
    @NotBlank(message = "真实姓名不能为空")
    private String realName;
    @NotNull(message = "性别不能为空")
    private Integer sex;
    @NotBlank(message = "昵称不能为空")
    private String nickName;
    @NotBlank(message = "手机号码不能为空")
    private String phone;
    @NotBlank(message = "邮箱地址不能为空")
    private String email;
    @NotBlank(message = "个性签名不能为空")
    private String signature;
    @NotBlank(message = "自我介绍不能为空")
    private String introduction;
}
