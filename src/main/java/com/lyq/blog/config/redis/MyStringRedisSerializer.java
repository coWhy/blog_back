package com.lyq.blog.config.redis;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import org.springframework.data.redis.serializer.RedisSerializer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * redis序列化解析工具类
 * 对redis value 进行序列化,支持传入Object
 *
 * @version 1.0
 * @author: 林义清
 * @date: 2020/4/18 1:12 上午
 */
public class MyStringRedisSerializer implements RedisSerializer<Object> {

    private final Charset charset;

    public MyStringRedisSerializer() {
        this(StandardCharsets.UTF_8);
    }

    public MyStringRedisSerializer(Charset charset) {
        Assert.notNull(charset, "字符集不能为空!");
        this.charset = charset;
    }

    @Override
    public String deserialize(byte[] bytes) {
        return (bytes == null ? null : new String(bytes, charset));
    }

    @Override
    public byte[] serialize(Object object) {
        if (object == null) {
            return new byte[0];
        }
        if (object instanceof String) {
            return object.toString().getBytes(charset);
        } else {
            String string = JSON.toJSONString(object);
            return string.getBytes(charset);
        }

    }

}
