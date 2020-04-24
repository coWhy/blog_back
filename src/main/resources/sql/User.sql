-- auto Generated on 2020-04-23
-- DROP TABLE IF EXISTS `user`;
CREATE TABLE `bl_user`
(
    user_id      VARCHAR(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '用户id 分布式id',
    is_admin     INT(1)                                                        NOT NULL DEFAULT 0 COMMENT '是否是管理员 1: 是 0: 不是 默认值为 0  必填',
    user_name    VARCHAR(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL DEFAULT '' COMMENT '用户名 必填',
    salt         VARCHAR(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL DEFAULT '' COMMENT '加密盐值 必填',
    `password`   VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '密码明文 必填',
    sex          INT(1)                                                        NOT NULL DEFAULT -1 COMMENT '性别 1: 男 0: 女 必填',
    phone        VARCHAR(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL DEFAULT '' COMMENT '电话号码  必填',
    email        VARCHAR(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL DEFAULT '' COMMENT '邮箱地址 必填',
    avatar       VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          DEFAULT '' COMMENT '用户头像 会给予一个默认头像 选填',
    nick_name    VARCHAR(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci           DEFAULT '' COMMENT '用户昵称 选填',
    introduction VARCHAR(511) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          DEFAULT '' COMMENT '自我介绍 选填 管理员-独享',
    signature    VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          DEFAULT '' COMMENT '个性签名选填 管理员独享',
    create_at    TIMESTAMP                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by    VARCHAR(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL DEFAULT '' COMMENT '创建人id',
    update_at    TIMESTAMP                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by    VARCHAR(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL DEFAULT '' COMMENT '更新人id',
    version      INT(11)                                                       NOT NULL DEFAULT 1 COMMENT '乐观锁 每次更新 version ++',
    deleted      INT(1)                                                        NOT NULL DEFAULT 0 COMMENT '逻辑删除 逻辑删除 1：删除 0 : 未删除 删除时候 deleted=1',
    PRIMARY KEY (user_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '用户表';
