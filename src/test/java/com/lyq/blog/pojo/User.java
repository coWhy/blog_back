package com.lyq.blog.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @Author: 林义清
 * @Date: 2020/4/23 9:43 下午
 * @Version: 1.0.0
 */

/**
 * 用户表
 */
@Data
public class User {
    /**
     * 用户id
     * 分布式id
     */
    private String userId;
    /**
     * 是否是管理员 1: 是 0: 不是
     * 默认值为 0
     * 必填
     */
    private Integer isAdmin;
    /**
     * 用户名
     * 必填
     */
    private String userName;
    /**
     * 加密盐值
     * 必填
     */
    private String salt;
    /**
     * 密码明文
     * 必填
     */
    private String password;
    /**
     * 性别 1: 男 0: 女
     * 必填
     */
    private Integer sex;
    /**
     * 电话号码
     * 必填
     */
    private String phone;
    /**
     * 邮箱地址
     * 必填
     */
    private String email;
    /**
     * 用户头像
     * 会给予一个默认头像
     * 选填
     */
    private String avatar;
    /**
     * 用户昵称
     * 选填
     */
    private String nickName;
    /**
     * 自我介绍
     * 选填
     * 管理员-独享
     */
    private String introduction;
    /**
     * 个性签名
     * 选填
     * 管理员独享
     */
    private String signature;
    /**
     * 创建时间
     */
    private Date createAt;
    /**
     * 创建人id
     */
    private String createBy;
    /**
     * 更新时间
     */
    private Date updateAt;
    /**
     * 更新人id
     */
    private String updateBy;
    /**
     * 乐观锁
     * 每次更新 version ++
     */
    private Integer version;
    /**
     * 逻辑删除
     * 逻辑删除 1：删除 0 : 未删除
     * 删除时候 deleted=1
     */
    private Integer deleted;
}

