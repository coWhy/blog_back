package com.lyq.blog.config.shiro;

import com.lyq.blog.config.shiro.custom.CusAccessControlFilter;
import com.lyq.blog.config.shiro.custom.CusAuthorizingRealm;
import com.lyq.blog.config.shiro.custom.CusHashedCredentialMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro核心配置
 *
 * @Author: 林义清
 * @Date: 2020/4/24 1:34 上午
 * @Version: 1.0.0
 */
@Configuration
public class ShiroConfig {
    /**
     * Shiro 自定义认证匹配重写
     *
     * @return
     */
    @Bean
    public CusHashedCredentialMatcher cusHashedCredentialMatcher() {
        return new CusHashedCredentialMatcher();
    }

    /**
     * 将 CusHashedCredentialMatcher 放入 realm
     * Shiro 自定义Realm
     *
     * @return
     */
    @Bean
    public CusAuthorizingRealm cusAuthorizingRealm() {
        CusAuthorizingRealm cusAuthorizingRealm = new CusAuthorizingRealm();
        cusAuthorizingRealm.setCredentialsMatcher(cusHashedCredentialMatcher());
        return cusAuthorizingRealm;
    }

    /**
     * 将 CusAuthorizingRealm 交给它处理
     * 安全管理器
     *
     * @return
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(cusAuthorizingRealm());
        return securityManager;
    }

    /**
     * Shiro过滤器，配置拦截哪些请求
     *
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //自定义拦截器限制并发人数：
        LinkedHashMap<String, Filter> filtersMap = new LinkedHashMap<>();
        //用来校验token
        filtersMap.put("token", new CusAccessControlFilter());
        shiroFilterFactoryBean.setFilters(filtersMap);
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/api/user/login", "anon");
        filterChainDefinitionMap.put("/api/test", "anon");

        filterChainDefinitionMap.put("/**", "token,authc");
        //配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
        shiroFilterFactoryBean.setLoginUrl("/api/user/unLogin");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
