package com.lyq.blog.config.mp;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.lyq.blog.constants.StateEnums;
import com.lyq.blog.utils.jwt.JwtTokenUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 自定义填充公共字段 ,即没有传的字段自动填充
 *
 * @Author: 林义清
 * @Date: 2020/4/24 2:07 上午
 * @Version: 1.0.0
 */
@Component
public class MetaHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
        System.err.println("正在调用该insert填充字段方法");
        String accessToken = (String) SecurityUtils.getSubject().getPrincipal();
        String userId = JwtTokenUtil.getUserId(accessToken);
        if (StringUtils.isBlank(userId)) {
            // 这里会抛出异常信息 不予理睬就行 jwt-getUserId(获取用户id错误信息): java.lang.NullPointerException: null
            // 假如不是管理员主动添加的话 就是系统默认生成 用户id = 1
            userId = StateEnums.SYSTEM.getCode().toString();
        }
        this.fillStrategy(metaObject, "createAt", new Date());
        this.fillStrategy(metaObject, "createBy", userId);
        System.err.println("调用该insert填充字段方法结束");
    }


    @Override
    public void updateFill(MetaObject metaObject) {
        System.err.println("正在调用该update填充字段方法");
        String accessToken = (String) SecurityUtils.getSubject().getPrincipal();
        String userId = JwtTokenUtil.getUserId(accessToken);
        if (StringUtils.isBlank(userId)) {
            // 这里会抛出异常信息 不予理睬就行 jwt-getUserId(获取用户id错误信息): java.lang.NullPointerException: null
            // 假如不是管理员主动添加的话 就是系统默认生成 用户id = 1
            userId = StateEnums.SYSTEM.getCode().toString();
        }
        System.err.println("Shiro获取的userId:" + userId);
        this.fillStrategy(metaObject, "updateAt", new Date());
        this.fillStrategy(metaObject, "updateBy", userId);
        System.err.println("调用该update填充字段方法结束");
    }
}
