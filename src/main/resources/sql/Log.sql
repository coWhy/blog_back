-- auto Generated on 2020-04-24
-- DROP TABLE IF EXISTS log;
CREATE TABLE bl_log
(
    log_id      BIGINT(20)                                                     NOT NULL AUTO_INCREMENT COMMENT '日志id',
    log_url     VARCHAR(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL DEFAULT '' COMMENT '请求路由',
    log_params  VARCHAR(4095) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '请求参数',
    log_status  INT(1)                                                         NOT NULL DEFAULT 1 COMMENT '访问状态 1:正常 0;异常',
    log_message MEDIUMTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '异常信息',
    log_method  VARCHAR(7) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci    NOT NULL DEFAULT '' COMMENT '请求方式 GET POST PUT DELETE',
    log_time    BIGINT(20)                                                     NOT NULL DEFAULT 0 COMMENT '响应时间',
    log_result  MEDIUMTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '响应返回信息',
    log_ip      VARCHAR(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NOT NULL DEFAULT '' COMMENT '请求人 ip地址',
    create_at   TIMESTAMP                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by   VARCHAR(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NOT NULL DEFAULT '' COMMENT '创建人id',
    PRIMARY KEY (log_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '日志表';
