package com.lyq.blog.service;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.lyq.blog.config.fastdfs.UploadConfig;
import com.lyq.blog.exception.BusinessException;
import com.lyq.blog.utils.result.code.ResponseCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * fastdfs 上传文件服务层
 *
 * @Author: 林义清
 * @Date: 2020/4/30 10:53 下午
 * @Version: 1.0.0
 */
@Component
@EnableConfigurationProperties(UploadConfig.class)
public class UploadService {
    private Log log = LogFactory.getLog(UploadService.class);

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private UploadConfig uploadConfig;

    public String uploadImage(MultipartFile file) {
        // 1、校验文件类型
        String contentType = file.getContentType();
        //文件上传类型在 配置文件中限制了 这里会报异常 文件类型不支持
        if (!uploadConfig.getAllowTypes().contains(contentType)) {
            throw new BusinessException(ResponseCode.UPLOAD_TYPE_NOT_SUPPORTED);
        }
        try {
            // 3、上传到FastDFS
            // 3.1、获取扩展名
            String extension = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
            // 3.2、上传
            StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), extension, null);
            // 返回路径
            return uploadConfig.getBaseUrl() + storePath.getFullPath();
        } catch (IOException e) {
            log.error("[文件上传]上传文件失败！....{}", e);
            throw new BusinessException(ResponseCode.UPLOAD_FILE_ERROR);
        }
    }
}
