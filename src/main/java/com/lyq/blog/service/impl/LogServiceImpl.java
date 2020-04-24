package com.lyq.blog.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyq.blog.entity.Log;
import com.lyq.blog.mapper.LogMapper;
import com.lyq.blog.service.LogService;
/**
 * @Author: 林义清
 * @Date: 2020/4/24 2:07 上午
 * @Version: 1.0.0 
 */
    
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService{

}
