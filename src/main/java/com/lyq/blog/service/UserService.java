package com.lyq.blog.service;

import com.lyq.blog.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyq.blog.vo.req.UserAddReqVo;
import com.lyq.blog.vo.req.UserLoginReqVo;
import com.lyq.blog.vo.resp.UserLoginRespVo;

/**
 * @Author: 林义清
 * @Date: 2020/4/23 10:38 下午
 * @Version: 1.0.0
 */

public interface UserService extends IService<User> {

    /**
     * 用户登录
     *
     * @param vo UserLoginReqVo
     * @return UserLoginRespVo
     */
    UserLoginRespVo login(UserLoginReqVo vo);

    /**
     * 根据用户id 查询用户信息
     *
     * @param id 用户id
     * @return User
     */
    User getUserById(String id);

    /**
     * 管理员添加新用户
     *
     * @param vo UserAddReqVo
     */
    void addUser(UserAddReqVo vo);

    /**
     * 用户退出登录
     *
     * @param accessToken  正常的业务token
     * @param refreshToken 刷新token
     */
    void logout(String accessToken, String refreshToken);
}
