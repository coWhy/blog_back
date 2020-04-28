package com.lyq.blog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyq.blog.constants.Constant;
import com.lyq.blog.entity.User;
import com.lyq.blog.service.UserService;
import com.lyq.blog.utils.result.CommonResult;
import com.lyq.blog.utils.result.code.ResponseCode;
import com.lyq.blog.vo.req.*;
import com.lyq.blog.vo.resp.UserLoginRespVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

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
     * 引导客户端去登录接口
     * 配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
     *
     * @return CommonResult<Object>
     */
    @GetMapping("/user/unlogin")
    public CommonResult<Object> unlogin() {
        return CommonResult.getResult(ResponseCode.TOKEN_ERROR);
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
     * 根据用户id 查询用户信息 接口
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
     * 用户分页模糊条件查询接口
     *
     * @param vo UserPageReqVo
     * @return CommonResult<Page < User>>
     */
    @PostMapping("/users/page")
    @RequiresPermissions("sys:user:page")
    public CommonResult<Page<User>> getUsersByPage(@RequestBody UserPageReqVo vo) {
        CommonResult result = new CommonResult();
        result.setMsg(ResponseCode.QUERY_SUCCESS.getMsg());
        result.setData(userService.getUsersByPage(vo));
        return result;
    }

    /**
     * 添加新用户接口
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

    /**
     * 新用户注册接口
     *
     * @param vo UserRegisterReqVo
     * @return CommonResult<Object>
     */
    @PostMapping("/user/register")
    public CommonResult<Object> registerUser(@RequestBody @Valid UserRegisterReqVo vo) {
        userService.registerUser(vo);
        CommonResult result = new CommonResult();
        result.setMsg(ResponseCode.REGISTER_SUCCESS.getMsg());
        return result;
    }

    /**
     * 根据id 删除用户 接口
     *
     * @param id 用户id
     * @return CommonResult<Object>
     */
    @DeleteMapping("/user/{id}")
    @RequiresPermissions("sys:user:del")
    public CommonResult<Object> delUserById(@PathVariable String id) {
        userService.delUserById(id);
        CommonResult result = new CommonResult();
        result.setMsg(ResponseCode.DELETE_SUCCESS.getMsg());
        return result;
    }

    /**
     * jwt刷新token 接口
     *
     * @param req HttpServletRequest
     * @return CommonResult<String>
     */
    @GetMapping("/user/token")
    public CommonResult<String> refreshToken(HttpServletRequest req) {
        // 从前端的header中拿出 过期时间长的refreshToken 这样做 是因为有可能前端的accessToken已经过期了 而刷新token的时间略长
        String refreshToken = req.getHeader(Constant.REFRESH_TOKEN);
        CommonResult result = new CommonResult();
        result.setData(userService.refreshToken(refreshToken));
        result.setMsg(ResponseCode.REFRESH_TOKEN_SUCCESS.getMsg());
        return result;
    }

    /**
     * 用户更新个人信息接口
     *
     * @param vo
     * @return
     */
    @PutMapping("/user")
    public CommonResult<Object> updateUserSelfInfo(@RequestBody @Valid UserUpdateReqVo vo) {
        userService.updateUserSelfInfo(vo);
        CommonResult result = new CommonResult();
        result.setMsg(ResponseCode.UPDATE_SUCCESS.getMsg());
        return result;
    }

    /**
     * 批量重置用户密码
     *
     * @param ids 用户ids
     * @return CommonResult<Object>
     */
    @PutMapping("/users/resetpwd")
    @RequiresPermissions("sys:user:resetpwd")
    public CommonResult<Object> batchResetUserPwd(@RequestBody List<String> ids) {
        userService.batchResetUserPwd(ids);
        CommonResult result = new CommonResult();
        result.setMsg(ResponseCode.RESET_PWD_SUCCESS.getMsg());
        return result;
    }

    /**
     * 用户重设个人密码接口
     * 这个地方的接口 管理员 和 用户 都可以用 因为只有登录了 才会有token 对于权限 已经很明确了
     *
     * @param vo UserResetPwdReqVo
     * @return CommonResult<Object>
     */
    @PutMapping("/user/resetpwd")
    public CommonResult<Object> userResetPwd(@RequestBody @Valid UserResetPwdReqVo vo, HttpServletRequest req) {
        // 由于更新个密码 是敏感操作 所以 需要重新刷新所有token 刷新 办法 就是引导重新登录 将过去的token拉入redis黑名单
        String accessToken = req.getHeader(Constant.ACCESS_TOKEN);
        String refreshToken = req.getHeader(Constant.REFRESH_TOKEN);
        userService.userResetPwd(vo, accessToken, refreshToken);
        CommonResult result = new CommonResult();
        result.setMsg(ResponseCode.RESET_PWD_SUCCESS.getMsg());
        return result;
    }

    @GetMapping("/user/admin/info")
    @RequiresPermissions("sys:admin:info")
    public CommonResult<User> getAdminInfo(){
        CommonResult result = new CommonResult();
        result.setData(userService.getAdminInfo());
        result.setMsg(ResponseCode.QUERY_SUCCESS.getMsg());
        return result;
    }
}
