package com.lyq.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.lyq.blog.constants.Constant;
import com.lyq.blog.constants.StateEnums;
import com.lyq.blog.exception.BusinessException;
import com.lyq.blog.service.RedisService;
import com.lyq.blog.utils.idworker.IdWorker;
import com.lyq.blog.utils.jwt.JwtTokenUtil;
import com.lyq.blog.utils.password.PasswordUtils;
import com.lyq.blog.utils.result.code.ResponseCode;
import com.lyq.blog.vo.req.UserAddReqVo;
import com.lyq.blog.vo.req.UserLoginReqVo;
import com.lyq.blog.vo.resp.UserLoginRespVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyq.blog.mapper.UserMapper;
import com.lyq.blog.entity.User;
import com.lyq.blog.service.UserService;

/**
 * @Author: 林义清
 * @Date: 2020/4/23 10:38 下午
 * @Version: 1.0.0
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private IdWorker idWorker;

    /**
     * 根据用户名查询用户 全部信息
     *
     * @param userName 用户名
     * @return User
     */
    private User getUserInfoByUserName(String userName) {
        if (StringUtils.isBlank(userName)) {
            throw new BusinessException(ResponseCode.DATA_INCOMING_ERROR);
        }
        return userMapper.selectOne(new QueryWrapper<User>()
                .eq("user_name", userName)
        );
    }

    /**
     * 用户登录
     * 登录成功后 我们会生成两个token：1.accseesToken  2.refreshToken（他们区别就是 过期时间不一样)
     * 生成后我们现在是直接返回给前端 前端就保存起来了
     * 那下次用户再请求进来的时候我们就要验证token的有效性了
     * 我们会判断它是是否失效 就是说 它登录后 后续的请求我们就直接校验这个token
     * 因为像以前是有状态的在他退出登录后我们直接在redis让它失效了就可以了
     * 但是现在是无状态 就是服务器端不保存 那就得反过来当他注销的时候我们才会标记起来
     * 因为token设置了有效期对 那肯定设计到自动刷新的功能
     * 保存在前端本地 或者cookie都可以
     *
     * 刷新token的作用是 : 当前端存入的accessToken过期了之后 前端传入refreshToken来进行token的刷新
     * 因为刷新token和访问token的最大区别就是过期时间不一样 所以只能用哪刷新token去刷新
     *
     * @param vo UserLoginReqVo
     * @return UserLoginRespVo
     */
    @Override
    public UserLoginRespVo login(UserLoginReqVo vo) {
        User loginUser = getUserInfoByUserName(vo.getUserName());
        //1.用户不存在
        if (loginUser == null) {
            throw new BusinessException(ResponseCode.ACCOUNT_ERROR);
        }
        //2.密码错误
        if (!PasswordUtils.matches(loginUser.getSalt(), vo.getPassword(), loginUser.getPassword())) {
            throw new BusinessException(ResponseCode.ACCOUNT_PASSWORD_ERROR);
        }
        //响应给前端的vo UserLoginRespVo 用于存放到token中
        //claims 存储在JWT里面的信息 一般放些用户的权限/角色信息 payload 这里我们存放 isAdmin字段信息
        //subject 一般存放用户id 也就是 这里的userId
        Map<String, Object> claims = new HashMap<>(2);
        //存入账号信息 userName 和 权限信息 isAdmin
        claims.put(Constant.JWT_USER_NAME, loginUser.getUserName());
        claims.put(Constant.JWT_IS_ADMIN, loginUser.getIsAdmin());
        //生成token
        String accessToken = JwtTokenUtil.getAccessToken(loginUser.getUserId(), claims);
        String refreshToken = JwtTokenUtil.getRefreshToken(loginUser.getUserId(), claims);
        return new UserLoginRespVo()
                .setAccessToken(accessToken)
                .setRefreshToken(refreshToken)
                .setUserName(loginUser.getUserName())
                .setIsAdmin(loginUser.getIsAdmin());
    }

    /**
     * 根据用户id 查询用户信息
     *
     * @param id 用户id
     * @return User
     */
    @Override
    public User getUserById(String id) {
        if (StringUtils.isBlank(id)) {
            throw new BusinessException(ResponseCode.DATA_INCOMING_ERROR);
        }
        User user = userMapper.selectOne(new QueryWrapper<User>()
                .select("user_id", "user_name", "nick_name", "avatar"
                        , "sex", "phone", "email",
                        "create_at", "create_by", "version")
                .eq("user_id", id)
                .eq("is_admin", StateEnums.USER.getCode())
                .eq("deleted", StateEnums.NOT_DELETED.getCode())
        );
        if (user == null) {
            throw new BusinessException(ResponseCode.QUERY_DATA_ERROR);
        }
        return user;
    }

    /**
     * 管理员添加新用户
     *
     * @param vo UserAddReqVo
     */
    @Override
    public void addUser(UserAddReqVo vo) {
        // 先根据用户名查询 用户是否存在  包括管理员 和 已经删除的用户群体
        User user = getUserInfoByUserName(vo.getUserName());
        if (user != null) {
            throw new BusinessException(ResponseCode.ACCOUNT_HAS_BEEN_REGISTERD);
        }
        user = new User();
        BeanUtils.copyProperties(vo, user);
        // 获取一个盐值
        String salt = PasswordUtils.getSalt();
        user.setUserId("user_" + idWorker.nextId());
        user.setPassword(PasswordUtils.encode(vo.getPassword(), salt));
        user.setSalt(salt);
        if (!save(user)) {
            throw new BusinessException(ResponseCode.SYSTEM_ERROR);
        }
    }

    /**
     * 用户退出登录
     *
     * @param accessToken  正常的业务token
     * @param refreshToken 刷新token
     */
    @Override
    public void logout(String accessToken, String refreshToken) {
        // 1.先校验这两个token是不是为空的 如果为空 返回全局异常信息
        if (StringUtils.isBlank(accessToken) || StringUtils.isBlank(refreshToken)) {
            throw new BusinessException(ResponseCode.DATA_INCOMING_ERROR);
        }
        // 从shiro中找到获取当前执行的用户
        Subject subject = SecurityUtils.getSubject();
        log.info("subject的主体(accessToken):{}", subject.getPrincipal());
        // 如果该用户 已经通过shiro拦截 并且完成了相关的认证 就进行注销服务
        if (subject.isAuthenticated()) {
            subject.logout();
        }
        // 获取当前注销用户的userId
        String userId = JwtTokenUtil.getUserId(accessToken);
        // 将这两个token放入redis里面 等待 过期时间全部到达
        // 因为 jwt token 一旦签发 就不能销毁 除非到达了过期时间
        // 所以为了防止被人再次利用 所以 通过redis 存入key值 进行 二次拦截
        // 这里我准备JWT_ACCESS_TOKEN_BLACKLIST 通过 (常量+accessToken,userId) 来存放
        // 也就是redis中 (key,value)
        redisService.set(Constant.JWT_ACCESS_TOKEN_BLACKLIST + accessToken, userId,
                JwtTokenUtil.getRemainingTime(accessToken), TimeUnit.MILLISECONDS);
        // 这里我准备JWT_REFRESH_TOKEN_BLACKLIST 通过 (常量+refreshToken,userId) 来存放
        // 也就是redis中 (key,value)
        redisService.set(Constant.JWT_REFRESH_TOKEN_BLACKLIST + refreshToken, userId,
                JwtTokenUtil.getRemainingTime(refreshToken), TimeUnit.MILLISECONDS);
    }
}
