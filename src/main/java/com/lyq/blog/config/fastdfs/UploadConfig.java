package com.lyq.blog.config.fastdfs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * fastdfs配置类
 *
 * @Author: 林义清
 * @Date: 2020/4/30 11:07 下午
 * @Version: 1.0.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "upload")
public class UploadConfig {
    private String baseUrl;
    private List<String> allowTypes;
}
