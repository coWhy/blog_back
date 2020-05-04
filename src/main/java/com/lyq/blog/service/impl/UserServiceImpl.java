package com.lyq.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyq.blog.constants.Constant;
import com.lyq.blog.constants.StateEnums;
import com.lyq.blog.exception.BusinessException;
import com.lyq.blog.service.RedisService;
import com.lyq.blog.utils.idworker.IdWorker;
import com.lyq.blog.utils.jwt.JwtTokenUtil;
import com.lyq.blog.utils.jwt.TokenSettings;
import com.lyq.blog.utils.password.PasswordUtils;
import com.lyq.blog.utils.result.code.ResponseCode;
import com.lyq.blog.vo.req.*;
import com.lyq.blog.vo.resp.UserLoginRespVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
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
    private TokenSettings tokenSettings;
    @Autowired
    private IdWorker idWorker;

    /**
     * 根据用户名查询用户 全部信息
     *
     * @param username 用户名
     * @return User
     */
    private User getUserInfoByUserName(String username) {
        if (StringUtils.isBlank(username)) {
            throw new BusinessException(ResponseCode.DATA_INCOMING_ERROR);
        }
        return userMapper.selectOne(new QueryWrapper<User>()
                .eq("username", username)
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
     * <p>
     * 刷新token的作用是 : 当前端存入的accessToken过期了之后 前端传入refreshToken来进行token的刷新
     * 因为刷新token和访问token的最大区别就是过期时间不一样 所以只能用哪刷新token去刷新
     *
     * @param vo UserLoginReqVo
     * @return UserLoginRespVo
     */
    @Override
    public UserLoginRespVo login(UserLoginReqVo vo) {
        User loginUser = getUserInfoByUserName(vo.getUsername());
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
        claims.put(Constant.JWT_USER_NAME, loginUser.getUsername());
        claims.put(Constant.JWT_IS_ADMIN, loginUser.getIsAdmin());
        //生成token
        String accessToken = JwtTokenUtil.getAccessToken(loginUser.getUserId(), claims);
        String refreshToken = JwtTokenUtil.getRefreshToken(loginUser.getUserId(), claims);
        // 更新登录时间
        loginUser.setLastLoginAt(new Date());
        updateById(loginUser);
        return new UserLoginRespVo()
                .setAccessToken(accessToken)
                .setRefreshToken(refreshToken)
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
                .select("user_id", "real_name", "username", "nick_name", "avatar"
                        , "sex", "phone", "email", "last_login_at",
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
        User user = getUserInfoByUserName(vo.getUsername());
        if (user != null) {
            throw new BusinessException(ResponseCode.ACCOUNT_HAS_BEEN_REGISTERD);
        }
        user = new User();
        BeanUtils.copyProperties(vo, user);
        // 获取一个盐值
        String salt = PasswordUtils.getSalt();
        user.setUserId("USER_" + idWorker.nextId());
        user.setPassword(PasswordUtils.encode(vo.getPassword(), salt));
        user.setSalt(salt);
        if (!save(user)) {
            throw new BusinessException(ResponseCode.OPERATION_ERROR);
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
        System.err.println("当前注销的用户是:" + userId);
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

    /**
     * 用户分页模糊条件查询接口
     *
     * @param vo UserPageReqVo
     * @return Page<User>
     */
    @Override
    public Page<User> getUsersByPage(UserPageReqVo vo) {
        Page<User> userPage = userMapper.selectPage(new Page<User>(vo.getPageNum(), vo.getPageSize()), new QueryWrapper<User>()
                .select("user_id", "username", "real_name", "nick_name", "avatar", "sex", "phone", "email", "last_login_at", "create_at", "create_by")
                .like(StringUtils.isNotBlank(vo.getUsername()), "username", vo.getUsername())
                .like(StringUtils.isNotBlank(vo.getPhone()), "phone", vo.getPhone())
                .like(StringUtils.isNotBlank(vo.getEmail()), "email", vo.getEmail())
                .eq("is_admin", StateEnums.USER.getCode())
                .eq("deleted", StateEnums.NOT_DELETED.getCode())
        );
        if (userPage == null) {
            throw new BusinessException(ResponseCode.QUERY_DATA_ERROR);
        }
        return userPage;
    }

    /**
     * 新用户注册
     *
     * @param vo UserRegisterReqVo
     */
    @Override
    public void registerUser(UserRegisterReqVo vo) {
        // 由于目前注册 和 新增用户的vo字段一样 所以 就直接用 倘若想更改 可以自行更改
        UserAddReqVo addReqVo = new UserAddReqVo();
        BeanUtils.copyProperties(vo, addReqVo);
        addUser(addReqVo);
    }

    /**
     * 根据id 删除用户 接口
     *
     * @param id 用户id
     */
    @Override
    public void delUserById(String id) {
        // 先确定删除的对象是不是管理员 管理员是不能删除的
        User user = userMapper.selectOne(new QueryWrapper<User>()
                .select("is_admin")
                .eq("user_id", id)
                .eq("deleted", StateEnums.NOT_DELETED.getCode())
        );
        if (user == null) {
            throw new BusinessException(ResponseCode.USELESS_OPERATION);
        }
        if (user.getIsAdmin().equals(StateEnums.ADMIN.getCode())) {
            throw new BusinessException(ResponseCode.NOT_DELETE_ADMIN);
        }
        userMapper.deleteById(id);
        // 如果当前的用户 还处于在线状态 就靠此操作 让shiro进行拦截 让该用户立即注销登录 具体 看 CusHashedCredentialMatcher 用户认证匹配里面的逻辑
        // 存入redis 删除key
        redisService.set(Constant.DELETED_USER_KEY + id, id,
                tokenSettings.getRefreshTokenExpireAppTime().toMillis(),
                TimeUnit.MILLISECONDS);
    }

    /**
     * jwt刷新token 接口
     *
     * @param refreshToken String 刷新token
     * @return String jwt token
     */
    @Override
    public String refreshToken(String refreshToken) {
        if (StringUtils.isBlank(refreshToken)) {
            throw new BusinessException(ResponseCode.DATA_INCOMING_ERROR);
        }
        //首先先判断这个刷新token是否过期  是否已经存入redis的黑名单key里面了这里 CusHashedCredentialMatcher 里面也有相关的shiro认证匹配拦截的逻辑
        if (!JwtTokenUtil.validateToken(refreshToken) || redisService.hasKey(Constant.JWT_REFRESH_KEY + refreshToken)) {
            throw new BusinessException(ResponseCode.TOKEN_ERROR);
        }
        // 假如刷新token是正常的 我们就来取出userId 因为登录的时候  放入了两个token 这个刷新token 和 访问token 没有 什么区别 只是 过期时间不一样
        // 所以可以取出userId
        String userId = JwtTokenUtil.getUserId(refreshToken);
        log.info("当前刷新用户id:{}", userId);
        Map<String, Object> claims = null;
        if (redisService.hasKey(Constant.JWT_REFRESH_KEY + userId)) {
            claims = new HashMap<>(2);
            User refreshUser = userMapper.selectOne(new QueryWrapper<User>()
                    .select("user_id", "username", "is_admin")
                    .eq("user_id", userId)
                    .eq("deleted", StateEnums.NOT_DELETED.getCode())
            );
            //重新存入账号信息 userName 和 权限信息 isAdmin
            claims.put(Constant.JWT_USER_NAME, refreshUser.getUsername());
            claims.put(Constant.JWT_IS_ADMIN, refreshUser.getIsAdmin());
        }
        // 返回 重新生成的accessToken
        return JwtTokenUtil.refreshToken(refreshToken, claims);
    }

    /**
     * 批量重置用户密码
     *
     * @param ids List<String>
     */
    @Override
    public void batchResetUserPwd(List<String> ids) {
        // 先查询后更新
        List<User> userList = userMapper.selectBatchIds(ids);
        if (userList.isEmpty()) {
            throw new BusinessException(ResponseCode.DATA_INCOMING_ERROR);
        }
        userList.forEach(user -> {
            user.setPassword(PasswordUtils.encode(Constant.USER_RESET_PWD, user.getSalt()));
            user.setVersion(user.getVersion());
            // 假如更新失败 就抛出错误信息
            if (!updateById(user)) {
                throw new BusinessException(ResponseCode.OPERATION_ERROR);
            }

        });
    }

    /**
     * 用户更新个人信息接口
     *
     * @param vo UserUpdateReqVo
     */
    @Override
    public void updateUserSelfInfo(UserUpdateReqVo vo) {
        //先查询后再进行更新
        User user = userMapper.selectOne(new QueryWrapper<User>()
                .select("version")
                .eq("user_id", vo.getUserId())
                .eq("is_admin", StateEnums.USER.getCode())
                .eq("deleted", StateEnums.NOT_DELETED.getCode())
        );
        ;
        if (user == null) {
            throw new BusinessException(ResponseCode.DATA_INCOMING_ERROR);
        }
        BeanUtils.copyProperties(vo, user);
        user.setVersion(user.getVersion());
        // 假如更新失败 就抛出错误信息
        if (!updateById(user)) {
            throw new BusinessException(ResponseCode.OPERATION_ERROR);
        }

    }

    /**
     * 用户重设个人密码接口
     *
     * @param vo           UserResetPwdReqVo
     * @param accessToken  访问token
     * @param refreshToken 刷新token
     */
    @Override
    public void userResetPwd(UserResetPwdReqVo vo, String accessToken, String refreshToken) {
        String userId = JwtTokenUtil.getUserId(accessToken);
        User user = userMapper.selectOne(new QueryWrapper<User>()
                .select("user_id", "salt", "password", "version")
                .eq("user_id", userId)
                .eq("deleted", StateEnums.NOT_DELETED.getCode())
        );
        if (user == null) {
            throw new BusinessException(ResponseCode.TOKEN_ERROR);
        }
        // 校验旧密码
        if (!PasswordUtils.matches(user.getSalt(), vo.getOldPassword(), user.getPassword())) {
            throw new BusinessException(ResponseCode.ACCOUNT_OLD_PWD_NOT_CORRECT);
        }
        //保存新密码
        user.setPassword(PasswordUtils.encode(vo.getNewPassword(), user.getSalt()));
        user.setVersion(user.getVersion());
        if (!updateById(user)) {
            throw new BusinessException(ResponseCode.OPERATION_ERROR);
        }
        // accessToken 拉入redis 黑名单 禁止再访问我们的系统资源
        redisService.set(Constant.JWT_ACCESS_TOKEN_BLACKLIST + accessToken, userId,
                JwtTokenUtil.getRemainingTime(accessToken), TimeUnit.MILLISECONDS);
        // refreshToken 拉入redis 黑名单 禁止再访问我们的系统资源
        redisService.set(Constant.JWT_REFRESH_TOKEN_BLACKLIST + refreshToken, userId,
                JwtTokenUtil.getRemainingTime(refreshToken), TimeUnit.MILLISECONDS);

    }

    /**
     * 获取管理员信息
     *
     * @return User
     */
    @Override
    public User getAdminInfo() {
        User user = userMapper.selectOne(new QueryWrapper<User>()
                .select("real_name", "nick_name", "avatar", "sex", "phone", "email", "introduction", "signature", "last_login_at")
                .eq("is_admin", StateEnums.ADMIN.getCode())
        );
        if (user == null) {
            throw new BusinessException(ResponseCode.QUERY_DATA_ERROR);
        }
        return user;
    }

    /**
     * 更换管理员头像
     *
     * @param accessToken 访问token
     * @param avatar      头像地址
     */
    @Override
    public void changeAdminAvatar(String accessToken, String avatar) {
        if (StringUtils.isBlank(avatar)) {
            throw new BusinessException(ResponseCode.DATA_INCOMING_ERROR);
        }
        // 先查询再更新管理员头像  这里为了更加安全 所以 加上is_admin
        User admin = userMapper.selectOne(new QueryWrapper<User>()
                .select("user_id", "version")
                .eq("user_id", JwtTokenUtil.getUserId(accessToken))
                .eq("is_admin", StateEnums.ADMIN.getCode())
        );
        // 如果没有查询到 就返回异常信息 用户未登录
        if (admin == null) {
            throw new BusinessException(ResponseCode.TOKEN_ERROR);
        }
        admin.setAvatar(avatar);
        admin.setVersion(admin.getVersion());
        if (!updateById(admin)) {
            throw new BusinessException(ResponseCode.OPERATION_ERROR);
        }
    }

    /**
     * 更新管理员个人信息接口
     *
     * @param accessToken 访问token
     * @param vo          AdminUpdateVo
     */
    @Override
    public void updateAdminInfo(String accessToken, AdminUpdateVo vo) {
        // 先查询再更新管理员信息 这里为了更加安全 所以 加上is_admin
        User admin = userMapper.selectOne(new QueryWrapper<User>()
                .select("user_id", "version")
                .eq("user_id", JwtTokenUtil.getUserId(accessToken))
                .eq("is_admin", StateEnums.ADMIN.getCode())
        );
        // 如果没有查询到 就返回异常信息 用户未登录
        if (admin == null) {
            throw new BusinessException(ResponseCode.TOKEN_ERROR);
        }
        BeanUtils.copyProperties(vo, admin);
        admin.setVersion(admin.getVersion());
        if (!updateById(admin)) {
            throw new BusinessException(ResponseCode.OPERATION_ERROR);
        }
    }

}
