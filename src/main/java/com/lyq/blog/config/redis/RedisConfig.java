package com.lyq.blog.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis配置类
 * 配置redis支持 (Key,Object)
 * key 还是默认的
 * value改成自定义的
 *
 * @version 1.0
 * @author: 林义清
 * @date: 2020/4/18 1:12 上午
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        StringRedisSerializer serializer = new StringRedisSerializer();
        MyStringRedisSerializer mySerializer = new MyStringRedisSerializer();
        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setHashKeySerializer(serializer);
        redisTemplate.setHashValueSerializer(mySerializer);
        redisTemplate.setValueSerializer(mySerializer);
        return redisTemplate;
    }
}
