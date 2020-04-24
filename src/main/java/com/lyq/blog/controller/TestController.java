package com.lyq.blog.controller;

import com.lyq.blog.utils.result.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 林义清
 * @Date: 2020/4/24 2:13 上午
 * @Version: 1.0.0
 */
@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/test")
    public CommonResult test() {
        return CommonResult.success("测试成功");
    }
}
