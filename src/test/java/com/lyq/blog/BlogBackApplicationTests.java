package com.lyq.blog;

import com.lyq.blog.pojo.User;
import com.lyq.blog.service.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogBackApplicationTests {
    @Autowired
    private RedisService redisService;

    @Test
    void contextLoads() {
        User user = new User();
        user.setNickName("垃圾");
        redisService.set("测试使用", user);
    }

}
