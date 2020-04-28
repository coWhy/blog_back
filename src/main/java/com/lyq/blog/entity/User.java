package com.lyq.blog.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 用户表
 *
 * @Author: 林义清
 * @Date: 2020/4/23 10:38 下午
 * @Version: 1.0.0
 */
@Data
@TableName(value = "bl_user")
public class User implements Serializable {
    /**
     * 用户id 分布式id
     */
    @TableId(value = "user_id", type = IdType.INPUT)
    private String userId;

    /**
     * 是否是管理员 1: 是 0: 不是 默认值为 0  必填
     */
    @TableField(value = "is_admin")
    private Integer isAdmin;

    /**
     * 用户名 必填
     */
    @TableField(value = "username")
    private String username;

    /**
     * 真实姓名
     */
    @TableField(value = "real_name")
    private String realName;


    /**
     * 更新时间
     */
    @TableField(value = "last_login_at")
    private Date lastLoginAt;


    /**
     * 加密盐值 必填
     */
    @TableField(value = "salt")
    private String salt;

    /**
     * 密码明文 必填
     */
    @TableField(value = "password")
    private String password;

    /**
     * 性别 1: 男 0: 女 必填
     */
    @TableField(value = "sex")
    private Integer sex;

    /**
     * 电话号码  必填
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 邮箱地址 必填
     */
    @TableField(value = "email")
    private String email;

    /**
     * 用户头像 会给予一个默认头像 选填
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 用户昵称 选填
     */
    @TableField(value = "nick_name")
    private String nickName;

    /**
     * 自我介绍 选填 管理员-独享
     */
    @TableField(value = "introduction")
    private String introduction;

    /**
     * 个性签名选填 管理员独享
     */
    @TableField(value = "signature")
    private String signature;

    /**
     * 创建时间
     */
    @TableField(value = "create_at", fill = FieldFill.INSERT)
    private Date createAt;

    /**
     * 创建人id
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_at", fill = FieldFill.UPDATE)
    private Date updateAt;

    /**
     * 更新人id
     */
    @TableField(value = "update_by", fill = FieldFill.UPDATE)
    private String updateBy;

    /**
     * 乐观锁 每次更新 version ++
     */
    @TableField(value = "version")
    @Version
    private Integer version;

    /**
     * 逻辑删除 逻辑删除 1：删除 0 : 未删除 删除时候 deleted=1
     */
    @TableField(value = "deleted")
    @TableLogic
    private Integer deleted;

    private static final long serialVersionUID = 1L;

    public static final String COL_USER_ID = "user_id";

    public static final String COL_IS_ADMIN = "is_admin";

    public static final String COL_USERNAME = "username";

    public static final String COL_REAL_NAME = "real_name";

    public static final String COL_LAST_LOGIN_AT = "last_login_at";

    public static final String COL_SALT = "salt";

    public static final String COL_PASSWORD = "password";

    public static final String COL_SEX = "sex";

    public static final String COL_PHONE = "phone";

    public static final String COL_EMAIL = "email";

    public static final String COL_AVATAR = "avatar";

    public static final String COL_NICK_NAME = "nick_name";

    public static final String COL_INTRODUCTION = "introduction";

    public static final String COL_SIGNATURE = "signature";

    public static final String COL_CREATE_AT = "create_at";

    public static final String COL_CREATE_BY = "create_by";

    public static final String COL_UPDATE_AT = "update_at";

    public static final String COL_UPDATE_BY = "update_by";

    public static final String COL_VERSION = "version";

    public static final String COL_DELETED = "deleted";
}