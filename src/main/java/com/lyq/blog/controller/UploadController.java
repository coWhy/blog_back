package com.lyq.blog.controller;

import com.lyq.blog.service.UploadService;
import com.lyq.blog.utils.result.CommonResult;
import com.lyq.blog.utils.result.code.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: 林义清
 * @Date: 2020/4/30 11:40 下午
 * @Version: 1.0.0
 */
@RestController
@RequestMapping("/api")
public class UploadController {
    @Autowired
    private UploadService uploadService;

    /**
     * 文件上传接口
     *
     * @param file
     * @return
     */
    @PostMapping("/upload/image")
    public CommonResult<String> uploadImage(MultipartFile file) {
        CommonResult result = new CommonResult();
        result.setMsg(ResponseCode.UPLOAD_IMAGE_SUCCESS.getMsg());
        System.err.println(file);
        result.setData(uploadService.uploadImage(file));
        return result;
    }
}
