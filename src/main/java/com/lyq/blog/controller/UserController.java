package com.lyq.blog.controller;

import com.lyq.blog.constants.Constant;
import com.lyq.blog.entity.User;
import com.lyq.blog.service.UserService;
import com.lyq.blog.utils.result.CommonResult;
import com.lyq.blog.utils.result.code.ResponseCode;
import com.lyq.blog.vo.req.UserAddReqVo;
import com.lyq.blog.vo.req.UserLoginReqVo;
import com.lyq.blog.vo.req.UserRegisterReqVo;
import com.lyq.blog.vo.resp.UserLoginRespVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @Author: 林义清
 * @Date: 2020/4/23 11:06 下午
 * @Version: 1.0.0
 */
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 登录接口
     *
     * @param vo UserLoginReqVo
     * @return CommonResult<UserLoginRespVo>
     */
    @PostMapping("/user/login")
    public CommonResult<UserLoginRespVo> login(@RequestBody @Valid UserLoginReqVo vo) {
        CommonResult result = new CommonResult();
        result.setMsg(ResponseCode.LOGIN_SUCCESS.getMsg());
        result.setData(userService.login(vo));
        return result;
    }

    /**
     * 用户登出接口
     *
     * @param req HttpServletRequest
     * @return CommonResult<Object>
     */
    @GetMapping("/user/logout")
    public CommonResult<Object> logout(HttpServletRequest req) {
        // 用户退出登录之后 前端将从本地缓存 或者 cookie中拿出
        // 登录时候存入的两个token：accessToken 和 refreshToken 在前端发送异步请求的 时候 将两个token放入header中
        // 这样后端方便拿取这两个token
        String accessToken = req.getHeader(Constant.ACCESS_TOKEN);
        String refreshToken = req.getHeader(Constant.REFRESH_TOKEN);
        userService.logout(accessToken, refreshToken);
        CommonResult result = new CommonResult();
        result.setMsg(ResponseCode.LOGOUT_SUCCESS.getMsg());
        return result;
    }

    /**
     * 根据用户id 查询用户信息
     *
     * @param id 用户id
     * @return CommonResult<User>
     */
    @GetMapping("/user/{id}")
    @RequiresPermissions("sys:user:info")
    public CommonResult<User> getUserById(@PathVariable String id) {
        CommonResult result = new CommonResult();
        result.setMsg(ResponseCode.QUERY_SUCCESS.getMsg());
        result.setData(userService.getUserById(id));
        return result;
    }

    /**
     * 添加新用户
     *
     * @param vo UserAddReqVo
     * @return CommonResult<Object>
     */
    @PostMapping("/user")
    @RequiresPermissions("sys:user:add")
    public CommonResult<Object> addUser(@RequestBody @Valid UserAddReqVo vo) {
        userService.addUser(vo);
        CommonResult result = new CommonResult();
        result.setMsg(ResponseCode.ADD_SUCCESS.getMsg());
        return result;
    }


    @PostMapping("/register")
    public CommonResult<Object> registerUser(@RequestBody @Valid UserRegisterReqVo vo) {
        CommonResult result = new CommonResult();
        result.setMsg(ResponseCode.REGISTER_SUCCESS.getMsg());
        return result;
    }
}
