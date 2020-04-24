package com.lyq.blog.config.mp;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author: 林义清
 * @Date: 2020/4/24 2:07 上午
 * @Version: 1.0.0
 */
@MapperScan(basePackages = {"com.lyq.blog.mapper"})
@Configuration
@EnableTransactionManagement
public class MybatisPlusConfig {
    /**
     * 支持分页插件
     *
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {//开启分页
        return new PaginationInterceptor();
    }

    /**
     * 支持乐观锁
     *
     * @return
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

}
