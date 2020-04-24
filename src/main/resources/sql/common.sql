/*
 Navicat Premium Data Transfer

 Source Server         : 本地连接
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : 127.0.0.1:3306
 Source Schema         : db_blog

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

 Date: 24/04/2020 04:21:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bl_log
-- ----------------------------
DROP TABLE IF EXISTS `bl_log`;
CREATE TABLE `bl_log` (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志id',
  `log_url` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '请求路由',
  `log_params` varchar(4095) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '请求参数',
  `log_status` int(11) NOT NULL DEFAULT '1' COMMENT '访问状态 1:正常 0;异常',
  `log_message` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '异常信息',
  `log_method` varchar(7) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '请求方式 GET POST PUT DELETE',
  `log_time` bigint(20) NOT NULL DEFAULT '0' COMMENT '响应时间',
  `log_result` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '响应返回信息',
  `log_ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '请求人 ip地址',
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人id',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='日志表';

-- ----------------------------
-- Records of bl_log
-- ----------------------------
BEGIN;
INSERT INTO `bl_log` VALUES (1, '/api/test', '[]', 1, NULL, 'GET', 9, '{\"code\":0,\"data\":\"测试成功\",\"msg\":\"操作成功\"}', '0:0:0:0:0:0:0:1', '2020-04-24 02:15:10', '1');
INSERT INTO `bl_log` VALUES (2, '/api/test', '[]', 1, NULL, 'GET', 10, '{\"code\":0,\"data\":\"测试成功\",\"msg\":\"操作成功\"}', '0:0:0:0:0:0:0:1', '2020-04-24 02:17:13', '1');
INSERT INTO `bl_log` VALUES (3, '/api/test', '[]', 1, NULL, 'GET', 9, '{\"code\":0,\"data\":\"测试成功\",\"msg\":\"操作成功\"}', '0:0:0:0:0:0:0:1', '2020-04-24 02:21:26', '911');
INSERT INTO `bl_log` VALUES (4, '/api/user', '[UserAddReqVo(userName=admin, password=111222, sex=1, nickName=清凛冬, phone=18061877017, email=18061877017@163.com)]', 1, NULL, 'POST', 671, '{\"code\":0,\"msg\":\"添加成功\"}', '0:0:0:0:0:0:0:1', '2020-04-24 03:45:16', '1');
INSERT INTO `bl_log` VALUES (5, '/api/user', '[UserAddReqVo(userName=admin, password=111222, sex=1, nickName=清凛冬, phone=18061877017, email=18061877017@163.com)]', 0, 'com.lyq.blog.exception.BusinessException: 用户名已存在,请换一个试试\n	at com.lyq.blog.service.impl.UserServiceImpl.addUser(UserServiceImpl.java:127)\n	at com.lyq.blog.service.impl.UserServiceImpl$$FastClassBySpringCGLIB$$124b148a.invoke(<generated>)\n	at com.lyq.blog.service.impl.UserServiceImpl$$EnhancerBySpringCGLIB$$1f8d759d.addUser(<generated>)\n	at com.lyq.blog.controller.UserController.addUser(UserController.java:64)\n	at com.lyq.blog.controller.UserController$$FastClassBySpringCGLIB$$3dcbf964.invoke(<generated>)\n	at com.lyq.blog.aop.aspect.LogAspect.doAround(LogAspect.java:62)\n	at com.lyq.blog.controller.UserController$$EnhancerBySpringCGLIB$$25f00c58.addUser(<generated>)\n', 'POST', 0, NULL, '0:0:0:0:0:0:0:1', '2020-04-24 03:47:09', '1');
INSERT INTO `bl_log` VALUES (6, '/api/test', '[]', 1, NULL, 'GET', 9, '{\"code\":0,\"data\":\"测试成功\",\"msg\":\"操作成功\"}', '0:0:0:0:0:0:0:1', '2020-04-24 03:48:50', '1');
INSERT INTO `bl_log` VALUES (7, '/api/test', '[]', 1, NULL, 'GET', 1, '{\"code\":0,\"data\":\"测试成功\",\"msg\":\"操作成功\"}', '0:0:0:0:0:0:0:1', '2020-04-24 03:50:28', '1');
INSERT INTO `bl_log` VALUES (8, '/api/test', '[]', 1, NULL, 'GET', 14, '{\"code\":0,\"data\":\"测试成功\",\"msg\":\"操作成功\"}', '0:0:0:0:0:0:0:1', '2020-04-24 03:50:57', '1');
INSERT INTO `bl_log` VALUES (9, '/api/test', '[]', 1, NULL, 'GET', 1, '{\"code\":0,\"data\":\"测试成功\",\"msg\":\"操作成功\"}', '0:0:0:0:0:0:0:1', '2020-04-24 03:51:31', '1');
INSERT INTO `bl_log` VALUES (10, '/api/user', '[UserAddReqVo(userName=admin, password=111222, sex=1, nickName=风萧萧, phone=18397923228, email=18397923228@163.com)]', 0, 'com.lyq.blog.exception.BusinessException: 用户名已存在,请换一个试试\n	at com.lyq.blog.service.impl.UserServiceImpl.addUser(UserServiceImpl.java:127)\n	at com.lyq.blog.service.impl.UserServiceImpl$$FastClassBySpringCGLIB$$124b148a.invoke(<generated>)\n	at com.lyq.blog.service.impl.UserServiceImpl$$EnhancerBySpringCGLIB$$e851140e.addUser(<generated>)\n	at com.lyq.blog.controller.UserController.addUser(UserController.java:64)\n	at com.lyq.blog.controller.UserController$$FastClassBySpringCGLIB$$3dcbf964.invoke(<generated>)\n	at com.lyq.blog.aop.aspect.LogAspect.doAround(LogAspect.java:62)\n	at com.lyq.blog.controller.UserController$$EnhancerBySpringCGLIB$$bdeddd15.addUser(<generated>)\n', 'POST', 0, NULL, '0:0:0:0:0:0:0:1', '2020-04-24 03:53:25', '1');
INSERT INTO `bl_log` VALUES (11, '/api/user', '[UserAddReqVo(userName=admin, password=111222, sex=1, nickName=风萧萧, phone=18397923228, email=18397923228@163.com)]', 0, 'com.lyq.blog.exception.BusinessException: 用户名已存在,请换一个试试\n	at com.lyq.blog.service.impl.UserServiceImpl.addUser(UserServiceImpl.java:127)\n	at com.lyq.blog.service.impl.UserServiceImpl$$FastClassBySpringCGLIB$$124b148a.invoke(<generated>)\n	at com.lyq.blog.service.impl.UserServiceImpl$$EnhancerBySpringCGLIB$$e851140e.addUser(<generated>)\n	at com.lyq.blog.controller.UserController.addUser(UserController.java:64)\n	at com.lyq.blog.controller.UserController$$FastClassBySpringCGLIB$$3dcbf964.invoke(<generated>)\n	at com.lyq.blog.aop.aspect.LogAspect.doAround(LogAspect.java:62)\n	at com.lyq.blog.controller.UserController$$EnhancerBySpringCGLIB$$bdeddd15.addUser(<generated>)\n', 'POST', 0, NULL, '0:0:0:0:0:0:0:1', '2020-04-24 03:53:27', '1');
INSERT INTO `bl_log` VALUES (12, '/api/user', '[UserAddReqVo(userName=dev, password=111222, sex=1, nickName=风萧萧, phone=18397923228, email=18397923228@163.com)]', 1, NULL, 'POST', 122, '{\"code\":0,\"msg\":\"添加成功\"}', '0:0:0:0:0:0:0:1', '2020-04-24 03:53:31', '1');
INSERT INTO `bl_log` VALUES (13, '/api/user', '[UserAddReqVo(userName=dev, password=111222, sex=1, nickName=风萧萧, phone=18397923228, email=18397923228@163.com)]', 0, 'com.lyq.blog.exception.BusinessException: 用户名已存在,请换一个试试\n	at com.lyq.blog.service.impl.UserServiceImpl.addUser(UserServiceImpl.java:127)\n	at com.lyq.blog.service.impl.UserServiceImpl$$FastClassBySpringCGLIB$$124b148a.invoke(<generated>)\n	at com.lyq.blog.service.impl.UserServiceImpl$$EnhancerBySpringCGLIB$$e851140e.addUser(<generated>)\n	at com.lyq.blog.controller.UserController.addUser(UserController.java:64)\n	at com.lyq.blog.controller.UserController$$FastClassBySpringCGLIB$$3dcbf964.invoke(<generated>)\n	at com.lyq.blog.aop.aspect.LogAspect.doAround(LogAspect.java:62)\n	at com.lyq.blog.controller.UserController$$EnhancerBySpringCGLIB$$bdeddd15.addUser(<generated>)\n', 'POST', 0, NULL, '0:0:0:0:0:0:0:1', '2020-04-24 03:55:39', '1');
INSERT INTO `bl_log` VALUES (14, '/api/user', '[UserAddReqVo(userName=sum, password=111222, sex=1, nickName=微笑的面对它, phone=18397923228, email=18397923228@163.com)]', 1, NULL, 'POST', 124, '{\"code\":0,\"msg\":\"添加成功\"}', '0:0:0:0:0:0:0:1', '2020-04-24 03:56:46', '1');
INSERT INTO `bl_log` VALUES (15, '/api/user/login', '[UserLoginReqVo(userName=sum, password=111222)]', 1, NULL, 'POST', 637, '{\"code\":0,\"data\":{\"accessToken\":\"eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYzdlZjkyMTMwMzEwOTMxNWZiYTY1ZTJlZmUwYzU0OSMiLCJzdWIiOiJ1c2VyXzEyNTM0MTI1MTgyMjc4Nzc4ODgiLCJqd3RfdXNlcl9uYW1lX2tleSI6InN1bSIsImV4cCI6MTU4NzY3OTEzOSwiaWF0IjoxNTg3NjcxOTM5LCJqd3RfaXNfYWRtaW4iOjB9.CY2BRdQXwEc29BVSfFwhCmdemuHUlLxC9twtXKZeTRY\",\"isAdmin\":0,\"refreshToken\":\"eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYzdlZjkyMTMwMzEwOTMxNWZiYTY1ZTJlZmUwYzU0OSMiLCJzdWIiOiJ1c2VyXzEyNTM0MTI1MTgyMjc4Nzc4ODgiLCJqd3RfdXNlcl9uYW1lX2tleSI6InN1bSIsImV4cCI6MTU4NzcwMDczOSwiaWF0IjoxNTg3NjcxOTM5LCJqd3RfaXNfYWRtaW4iOjB9.XIik1baBSGUtY7AZTuhM_5FcclvzYQj54Juz8cMiF04\",\"userName\":\"sum\"},\"msg\":\"登录成功\"}', '0:0:0:0:0:0:0:1', '2020-04-24 03:58:59', '1');
INSERT INTO `bl_log` VALUES (16, '/api/user/login', '[UserLoginReqVo(userName=dev, password=111222)]', 1, NULL, 'POST', 539, '{\"code\":0,\"data\":{\"accessToken\":\"eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYzdlZjkyMTMwMzEwOTMxNWZiYTY1ZTJlZmUwYzU0OSMiLCJzdWIiOiJ1c2VyXzEyNTM0MTE2OTg1NjQ0MDcyOTYiLCJqd3RfdXNlcl9uYW1lX2tleSI6ImRldiIsImV4cCI6MTU4NzY4MDAxOSwiaWF0IjoxNTg3NjcyODE5LCJqd3RfaXNfYWRtaW4iOjB9.a6tIqOdQB7wfW9EztKTr9oaSLpLieUouuzo_hfVuGd0\",\"isAdmin\":0,\"refreshToken\":\"eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYzdlZjkyMTMwMzEwOTMxNWZiYTY1ZTJlZmUwYzU0OSMiLCJzdWIiOiJ1c2VyXzEyNTM0MTE2OTg1NjQ0MDcyOTYiLCJqd3RfdXNlcl9uYW1lX2tleSI6ImRldiIsImV4cCI6MTU4NzcwMTYxOSwiaWF0IjoxNTg3NjcyODE5LCJqd3RfaXNfYWRtaW4iOjB9.9JFYEWzAxOLkHldXJNlVAzL8N5MiPUYUQFsLC9WKzvY\",\"userName\":\"dev\"},\"msg\":\"登录成功\"}', '0:0:0:0:0:0:0:1', '2020-04-24 04:13:40', '1');
INSERT INTO `bl_log` VALUES (17, '/api/user/login', '[UserLoginReqVo(userName=admin, password=111222)]', 1, NULL, 'POST', 10, '{\"code\":0,\"data\":{\"accessToken\":\"eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYzdlZjkyMTMwMzEwOTMxNWZiYTY1ZTJlZmUwYzU0OSMiLCJzdWIiOiJhZG1pbl8xMjUzNDA5NjIxMjY0MDg0OTkyIiwiand0X3VzZXJfbmFtZV9rZXkiOiJhZG1pbiIsImV4cCI6MTU4NzY4MDA3NiwiaWF0IjoxNTg3NjcyODc2LCJqd3RfaXNfYWRtaW4iOjF9.vlpLYCLwJTdzrvwIsqSyKOOKo2kuvHKKl5Vn2VOMdqA\",\"isAdmin\":1,\"refreshToken\":\"eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYzdlZjkyMTMwMzEwOTMxNWZiYTY1ZTJlZmUwYzU0OSMiLCJzdWIiOiJhZG1pbl8xMjUzNDA5NjIxMjY0MDg0OTkyIiwiand0X3VzZXJfbmFtZV9rZXkiOiJhZG1pbiIsImV4cCI6MTU4NzcwMTY3NiwiaWF0IjoxNTg3NjcyODc2LCJqd3RfaXNfYWRtaW4iOjF9.VB94dL9gzKJxpb0_ebpdxs4PL3Sjj055c70KiIEqCrc\",\"userName\":\"admin\"},\"msg\":\"登录成功\"}', '0:0:0:0:0:0:0:1', '2020-04-24 04:14:36', '1');
INSERT INTO `bl_log` VALUES (18, '/api/user', '[UserAddReqVo(userName=sum, password=111222, sex=1, nickName=微笑的面对它, phone=18397923228, email=18397923228@163.com)]', 0, 'com.lyq.blog.exception.BusinessException: 用户名已存在,请换一个试试\n	at com.lyq.blog.service.impl.UserServiceImpl.addUser(UserServiceImpl.java:127)\n	at com.lyq.blog.service.impl.UserServiceImpl$$FastClassBySpringCGLIB$$124b148a.invoke(<generated>)\n	at com.lyq.blog.service.impl.UserServiceImpl$$EnhancerBySpringCGLIB$$a6551540.addUser(<generated>)\n	at com.lyq.blog.controller.UserController.addUser(UserController.java:65)\n	at com.lyq.blog.controller.UserController$$FastClassBySpringCGLIB$$3dcbf964.invoke(<generated>)\n	at com.lyq.blog.aop.aspect.LogAspect.doAround(LogAspect.java:62)\n	at com.lyq.blog.controller.UserController$$EnhancerBySpringCGLIB$$f3187d93.addUser(<generated>)\n', 'POST', 0, NULL, '0:0:0:0:0:0:0:1', '2020-04-24 04:16:33', 'admin_1253409621264084992');
INSERT INTO `bl_log` VALUES (19, '/api/user/login', '[UserLoginReqVo(userName=dev, password=111222)]', 1, NULL, 'POST', 27, '{\"code\":0,\"data\":{\"accessToken\":\"eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYzdlZjkyMTMwMzEwOTMxNWZiYTY1ZTJlZmUwYzU0OSMiLCJzdWIiOiJ1c2VyXzEyNTM0MTE2OTg1NjQ0MDcyOTYiLCJqd3RfdXNlcl9uYW1lX2tleSI6ImRldiIsImV4cCI6MTU4NzY4MDIwMiwiaWF0IjoxNTg3NjczMDAyLCJqd3RfaXNfYWRtaW4iOjB9.dDsAo-eC0dKSSQUcOYZ25xlrW8RayzKFMoj3tUgzg64\",\"isAdmin\":0,\"refreshToken\":\"eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYzdlZjkyMTMwMzEwOTMxNWZiYTY1ZTJlZmUwYzU0OSMiLCJzdWIiOiJ1c2VyXzEyNTM0MTE2OTg1NjQ0MDcyOTYiLCJqd3RfdXNlcl9uYW1lX2tleSI6ImRldiIsImV4cCI6MTU4NzcwMTgwMiwiaWF0IjoxNTg3NjczMDAyLCJqd3RfaXNfYWRtaW4iOjB9.xL2vxHYYmSL7juETyDEvc04tPrulbzXf2dhqttySV_U\",\"userName\":\"dev\"},\"msg\":\"登录成功\"}', '0:0:0:0:0:0:0:1', '2020-04-24 04:16:42', 'admin_1253409621264084992');
INSERT INTO `bl_log` VALUES (20, '/api/user/login', '[UserLoginReqVo(userName=admin, password=111222)]', 1, NULL, 'POST', 11, '{\"code\":0,\"data\":{\"accessToken\":\"eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYzdlZjkyMTMwMzEwOTMxNWZiYTY1ZTJlZmUwYzU0OSMiLCJzdWIiOiJhZG1pbl8xMjUzNDA5NjIxMjY0MDg0OTkyIiwiand0X3VzZXJfbmFtZV9rZXkiOiJhZG1pbiIsImV4cCI6MTU4NzY4MDMyNywiaWF0IjoxNTg3NjczMTI3LCJqd3RfaXNfYWRtaW4iOjF9.fej_aXpCy-Kv9_TXDwcW2xsaxugGJnmbIecSguh6BdQ\",\"isAdmin\":1,\"refreshToken\":\"eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmYzdlZjkyMTMwMzEwOTMxNWZiYTY1ZTJlZmUwYzU0OSMiLCJzdWIiOiJhZG1pbl8xMjUzNDA5NjIxMjY0MDg0OTkyIiwiand0X3VzZXJfbmFtZV9rZXkiOiJhZG1pbiIsImV4cCI6MTU4NzcwMTkyNywiaWF0IjoxNTg3NjczMTI3LCJqd3RfaXNfYWRtaW4iOjF9.SHcpOR_3G6LLuPQjzGhkFodQrEsRqYKSFEdN3uljGjU\",\"userName\":\"admin\"},\"msg\":\"登录成功\"}', '0:0:0:0:0:0:0:1', '2020-04-24 04:18:47', 'user_1253411698564407296');
INSERT INTO `bl_log` VALUES (21, '/api/user/admin_1253409621264084992', '[admin_1253409621264084992]', 0, 'com.lyq.blog.exception.BusinessException: 查询不到此信息\n	at com.lyq.blog.service.impl.UserServiceImpl.getUserById(UserServiceImpl.java:112)\n	at com.lyq.blog.service.impl.UserServiceImpl$$FastClassBySpringCGLIB$$124b148a.invoke(<generated>)\n	at com.lyq.blog.service.impl.UserServiceImpl$$EnhancerBySpringCGLIB$$a6551540.getUserById(<generated>)\n	at com.lyq.blog.controller.UserController.getUserById(UserController.java:53)\n	at com.lyq.blog.controller.UserController$$FastClassBySpringCGLIB$$3dcbf964.invoke(<generated>)\n	at com.lyq.blog.aop.aspect.LogAspect.doAround(LogAspect.java:62)\n	at com.lyq.blog.controller.UserController$$EnhancerBySpringCGLIB$$f3187d93.getUserById(<generated>)\n', 'GET', 0, NULL, '0:0:0:0:0:0:0:1', '2020-04-24 04:16:33', 'admin_1253409621264084992');
INSERT INTO `bl_log` VALUES (22, '/api/user/user_1253411698564407296', '[user_1253411698564407296]', 1, NULL, 'GET', 11, '{\"code\":0,\"data\":{\"avatar\":\"\",\"createAt\":1587642917000,\"createBy\":\"1\",\"email\":\"18397923228@163.com\",\"nickName\":\"风萧萧\",\"phone\":\"18397923228\",\"sex\":1,\"userId\":\"user_1253411698564407296\",\"userName\":\"dev\",\"version\":1},\"msg\":\"查询成功\"}', '0:0:0:0:0:0:0:1', '2020-04-24 04:16:42', 'admin_1253409621264084992');
INSERT INTO `bl_log` VALUES (23, '/api/user/user_1253411698564407296', '[user_1253411698564407296]', 1, NULL, 'GET', 413, '{\"code\":0,\"data\":{\"avatar\":\"\",\"createAt\":1587642917000,\"createBy\":\"1\",\"email\":\"18397923228@163.com\",\"nickName\":\"风萧萧\",\"phone\":\"18397923228\",\"sex\":1,\"userId\":\"user_1253411698564407296\",\"userName\":\"dev\",\"version\":1},\"msg\":\"查询成功\"}', '0:0:0:0:0:0:0:1', '2020-04-24 04:20:12', 'admin_1253409621264084992');
INSERT INTO `bl_log` VALUES (24, '/api/user/admin_1253409621264084992', '[admin_1253409621264084992]', 0, 'com.lyq.blog.exception.BusinessException: 查询不到您需要的信息\n	at com.lyq.blog.service.impl.UserServiceImpl.getUserById(UserServiceImpl.java:112)\n	at com.lyq.blog.service.impl.UserServiceImpl$$FastClassBySpringCGLIB$$124b148a.invoke(<generated>)\n	at com.lyq.blog.service.impl.UserServiceImpl$$EnhancerBySpringCGLIB$$7f9a48fa.getUserById(<generated>)\n	at com.lyq.blog.controller.UserController.getUserById(UserController.java:53)\n	at com.lyq.blog.controller.UserController$$FastClassBySpringCGLIB$$3dcbf964.invoke(<generated>)\n	at com.lyq.blog.aop.aspect.LogAspect.doAround(LogAspect.java:62)\n	at com.lyq.blog.controller.UserController$$EnhancerBySpringCGLIB$$9c844de3.getUserById(<generated>)\n', 'GET', 0, NULL, '0:0:0:0:0:0:0:1', '2020-04-24 04:20:16', 'admin_1253409621264084992');
INSERT INTO `bl_log` VALUES (25, '/api/user/admin_1253409621264084992', '[admin_1253409621264084992]', 0, 'com.lyq.blog.exception.BusinessException: 查询不到您需要的信息\n	at com.lyq.blog.service.impl.UserServiceImpl.getUserById(UserServiceImpl.java:112)\n	at com.lyq.blog.service.impl.UserServiceImpl$$FastClassBySpringCGLIB$$124b148a.invoke(<generated>)\n	at com.lyq.blog.service.impl.UserServiceImpl$$EnhancerBySpringCGLIB$$7f9a48fa.getUserById(<generated>)\n	at com.lyq.blog.controller.UserController.getUserById(UserController.java:53)\n	at com.lyq.blog.controller.UserController$$FastClassBySpringCGLIB$$3dcbf964.invoke(<generated>)\n	at com.lyq.blog.aop.aspect.LogAspect.doAround(LogAspect.java:62)\n	at com.lyq.blog.controller.UserController$$EnhancerBySpringCGLIB$$9c844de3.getUserById(<generated>)\n', 'GET', 0, NULL, '0:0:0:0:0:0:0:1', '2020-04-24 04:20:18', 'admin_1253409621264084992');
INSERT INTO `bl_log` VALUES (26, '/api/user/admin_1253409621264084992', '[admin_1253409621264084992]', 0, 'com.lyq.blog.exception.BusinessException: 查询不到您需要的信息\n	at com.lyq.blog.service.impl.UserServiceImpl.getUserById(UserServiceImpl.java:112)\n	at com.lyq.blog.service.impl.UserServiceImpl$$FastClassBySpringCGLIB$$124b148a.invoke(<generated>)\n	at com.lyq.blog.service.impl.UserServiceImpl$$EnhancerBySpringCGLIB$$7f9a48fa.getUserById(<generated>)\n	at com.lyq.blog.controller.UserController.getUserById(UserController.java:53)\n	at com.lyq.blog.controller.UserController$$FastClassBySpringCGLIB$$3dcbf964.invoke(<generated>)\n	at com.lyq.blog.aop.aspect.LogAspect.doAround(LogAspect.java:62)\n	at com.lyq.blog.controller.UserController$$EnhancerBySpringCGLIB$$9c844de3.getUserById(<generated>)\n', 'GET', 0, NULL, '0:0:0:0:0:0:0:1', '2020-04-24 04:20:18', 'admin_1253409621264084992');
INSERT INTO `bl_log` VALUES (27, '/api/user/admin_1253409621264084992', '[admin_1253409621264084992]', 0, 'com.lyq.blog.exception.BusinessException: 查询不到您需要的信息\n	at com.lyq.blog.service.impl.UserServiceImpl.getUserById(UserServiceImpl.java:112)\n	at com.lyq.blog.service.impl.UserServiceImpl$$FastClassBySpringCGLIB$$124b148a.invoke(<generated>)\n	at com.lyq.blog.service.impl.UserServiceImpl$$EnhancerBySpringCGLIB$$7f9a48fa.getUserById(<generated>)\n	at com.lyq.blog.controller.UserController.getUserById(UserController.java:53)\n	at com.lyq.blog.controller.UserController$$FastClassBySpringCGLIB$$3dcbf964.invoke(<generated>)\n	at com.lyq.blog.aop.aspect.LogAspect.doAround(LogAspect.java:62)\n	at com.lyq.blog.controller.UserController$$EnhancerBySpringCGLIB$$9c844de3.getUserById(<generated>)\n', 'GET', 0, NULL, '0:0:0:0:0:0:0:1', '2020-04-24 04:20:19', 'admin_1253409621264084992');
INSERT INTO `bl_log` VALUES (28, '/api/user/admin_1253409621264084992', '[admin_1253409621264084992]', 0, 'com.lyq.blog.exception.BusinessException: 查询不到您需要的信息\n	at com.lyq.blog.service.impl.UserServiceImpl.getUserById(UserServiceImpl.java:112)\n	at com.lyq.blog.service.impl.UserServiceImpl$$FastClassBySpringCGLIB$$124b148a.invoke(<generated>)\n	at com.lyq.blog.service.impl.UserServiceImpl$$EnhancerBySpringCGLIB$$7f9a48fa.getUserById(<generated>)\n	at com.lyq.blog.controller.UserController.getUserById(UserController.java:53)\n	at com.lyq.blog.controller.UserController$$FastClassBySpringCGLIB$$3dcbf964.invoke(<generated>)\n	at com.lyq.blog.aop.aspect.LogAspect.doAround(LogAspect.java:62)\n	at com.lyq.blog.controller.UserController$$EnhancerBySpringCGLIB$$9c844de3.getUserById(<generated>)\n', 'GET', 0, NULL, '0:0:0:0:0:0:0:1', '2020-04-24 04:20:20', 'admin_1253409621264084992');
INSERT INTO `bl_log` VALUES (29, '/api/user/admin_1253409621264084992', '[admin_1253409621264084992]', 0, 'com.lyq.blog.exception.BusinessException: 查询不到您需要的信息\n	at com.lyq.blog.service.impl.UserServiceImpl.getUserById(UserServiceImpl.java:112)\n	at com.lyq.blog.service.impl.UserServiceImpl$$FastClassBySpringCGLIB$$124b148a.invoke(<generated>)\n	at com.lyq.blog.service.impl.UserServiceImpl$$EnhancerBySpringCGLIB$$7f9a48fa.getUserById(<generated>)\n	at com.lyq.blog.controller.UserController.getUserById(UserController.java:53)\n	at com.lyq.blog.controller.UserController$$FastClassBySpringCGLIB$$3dcbf964.invoke(<generated>)\n	at com.lyq.blog.aop.aspect.LogAspect.doAround(LogAspect.java:62)\n	at com.lyq.blog.controller.UserController$$EnhancerBySpringCGLIB$$9c844de3.getUserById(<generated>)\n', 'GET', 0, NULL, '0:0:0:0:0:0:0:1', '2020-04-24 04:20:20', 'admin_1253409621264084992');
INSERT INTO `bl_log` VALUES (30, '/api/user/admin_1253409621264084992', '[admin_1253409621264084992]', 0, 'com.lyq.blog.exception.BusinessException: 查询不到您需要的信息\n	at com.lyq.blog.service.impl.UserServiceImpl.getUserById(UserServiceImpl.java:112)\n	at com.lyq.blog.service.impl.UserServiceImpl$$FastClassBySpringCGLIB$$124b148a.invoke(<generated>)\n	at com.lyq.blog.service.impl.UserServiceImpl$$EnhancerBySpringCGLIB$$7f9a48fa.getUserById(<generated>)\n	at com.lyq.blog.controller.UserController.getUserById(UserController.java:53)\n	at com.lyq.blog.controller.UserController$$FastClassBySpringCGLIB$$3dcbf964.invoke(<generated>)\n	at com.lyq.blog.aop.aspect.LogAspect.doAround(LogAspect.java:62)\n	at com.lyq.blog.controller.UserController$$EnhancerBySpringCGLIB$$9c844de3.getUserById(<generated>)\n', 'GET', 0, NULL, '0:0:0:0:0:0:0:1', '2020-04-24 04:20:21', 'admin_1253409621264084992');
INSERT INTO `bl_log` VALUES (31, '/api/user/admin_1253409621264084992', '[admin_1253409621264084992]', 0, 'com.lyq.blog.exception.BusinessException: 查询不到您所需要的信息\n	at com.lyq.blog.service.impl.UserServiceImpl.getUserById(UserServiceImpl.java:112)\n	at com.lyq.blog.service.impl.UserServiceImpl$$FastClassBySpringCGLIB$$124b148a.invoke(<generated>)\n	at com.lyq.blog.service.impl.UserServiceImpl$$EnhancerBySpringCGLIB$$83fa0778.getUserById(<generated>)\n	at com.lyq.blog.controller.UserController.getUserById(UserController.java:53)\n	at com.lyq.blog.controller.UserController$$FastClassBySpringCGLIB$$3dcbf964.invoke(<generated>)\n	at com.lyq.blog.aop.aspect.LogAspect.doAround(LogAspect.java:62)\n	at com.lyq.blog.controller.UserController$$EnhancerBySpringCGLIB$$99d377aa.getUserById(<generated>)\n', 'GET', 0, NULL, '0:0:0:0:0:0:0:1', '2020-04-24 04:20:54', 'admin_1253409621264084992');
COMMIT;

-- ----------------------------
-- Table structure for bl_user
-- ----------------------------
DROP TABLE IF EXISTS `bl_user`;
CREATE TABLE `bl_user` (
  `user_id` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id 分布式id',
  `is_admin` int(11) NOT NULL DEFAULT '0' COMMENT '是否是管理员 1: 是 0: 不是 默认值为 0  必填',
  `user_name` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户名 必填',
  `salt` varchar(63) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '加密盐值 必填',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '密码明文 必填',
  `sex` int(11) NOT NULL DEFAULT '-1' COMMENT '性别 1: 男 0: 女 必填',
  `phone` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '电话号码  必填',
  `email` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '邮箱地址 必填',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '用户头像 会给予一个默认头像 选填',
  `nick_name` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '用户昵称 选填',
  `introduction` varchar(511) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '自我介绍 选填 管理员-独享',
  `signature` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '个性签名选填 管理员独享',
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人id',
  `update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(31) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '更新人id',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁 每次更新 version ++',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除 逻辑删除 1：删除 0 : 未删除 删除时候 deleted=1',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

-- ----------------------------
-- Records of bl_user
-- ----------------------------
BEGIN;
INSERT INTO `bl_user` VALUES ('admin_1253409621264084992', 1, 'admin', '1ddcb7b6f3b041f2b6a8', '349baa08a8ad6dcf5d55d1150522b58f', 1, '18061877017', '18061877017@163.com', '/admin.jpg', '清凛冬', '寻找未来的我 就是我了', '我用双手成就你的梦想', '2020-04-23 19:55:30', '1', '2020-04-23 19:55:30', '', 1, 0);
INSERT INTO `bl_user` VALUES ('user_1253411698564407296', 0, 'dev', '92a049b17773490c8ff3', '968cc793da30eddb6d3c56cd4f72a15f', 1, '18397923228', '18397923228@163.com', '', '风萧萧', '', '', '2020-04-23 19:55:17', '1', '2020-04-23 19:55:17', '', 1, 0);
INSERT INTO `bl_user` VALUES ('user_1253412518227877888', 0, 'sum', '7d01b4b66b6241a99e04', 'b54b9371cfbb6a3bd0d02f865226eb71', 1, '18397923228', '18397923228@163.com', '', '微笑的面对它', '', '', '2020-04-24 03:56:46', '1', '2020-04-23 19:56:46', '', 1, 0);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
