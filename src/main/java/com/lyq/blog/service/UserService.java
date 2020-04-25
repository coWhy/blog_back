package com.lyq.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyq.blog.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyq.blog.vo.req.*;
import com.lyq.blog.vo.resp.UserLoginRespVo;

import java.util.List;

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

    /**
     * 用户分页模糊条件查询接口
     *
     * @param vo UserPageReqVo
     * @return Page<User>
     */
    Page<User> getUsersByPage(UserPageReqVo vo);

    /**
     * 新用户注册
     *
     * @param vo UserRegisterReqVo
     */
    void registerUser(UserRegisterReqVo vo);

    /**
     * 根据id 删除用户 接口
     *
     * @param id 用户id
     */
    void delUserById(String id);

    /**
     * jwt刷新token 接口
     *
     * @param refreshToken String 刷新token
     * @return String jwt token
     */
    String refreshToken(String refreshToken);

    /**
     * 批量重置用户密码
     *
     * @param ids List<String>
     */
    void batchResetUserPwd(List<String> ids);

    /**
     * 用户更新个人信息接口
     *
     * @param vo UserUpdateReqVo
     */
    void updateUserSelfInfo(UserUpdateReqVo vo);

}
