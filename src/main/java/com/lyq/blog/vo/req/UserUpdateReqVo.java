package com.lyq.blog.vo.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 用户更新vo
 * 前提说明 用户名不能更新
 * 唯一存在
 *
 * @Author: 林义清
 * @Date: 2020/4/25 6:02 上午
 * @Version: 1.0.0
 */
@Data
public class UserUpdateReqVo {
    @NotBlank(message = "用户id不能为空")
    private String userId;
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
