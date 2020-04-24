package com.lyq.blog.config.bean;

import com.lyq.blog.utils.idworker.IdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Author: 林义清
 * @Date: 2020/4/24 2:07 上午
 * @Version: 1.0.0
 */
@Configuration
public class BeanConfig {
    /**
     * 分布式id生成器
     *
     * @return
     */
    @Bean
    public IdWorker idWorker() {
        return new IdWorker();
    }

}