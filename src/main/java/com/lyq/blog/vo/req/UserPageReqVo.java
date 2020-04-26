package com.lyq.blog.vo.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用户分页模糊查询vo
 *
 * @Author: 林义清
 * @Date: 2020/4/25 4:01 上午
 * @Version: 1.0.0
 */
@Data
public class UserPageReqVo {
    /**
     * 当前第几页
     */
    private Long pageNum;
    /**
     * 当前页数量
     */
    private Long pageSize;
    /**
     * 用户名
     */
    private String username;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 邮箱地址
     */
    private String email;
}
