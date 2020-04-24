package com.lyq.blog.utils.result.code;

/**
 * code工具类
 *
 * @version 1.0
 * @author: 林义清
 * @date: 2020/4/18 1:12 上午
 */
public interface ResponseCodeInterface {
    /**
     * 获取响应code
     *
     * @return
     */
    int getCode();

    /**
     * 获取响应客户端的提示
     *
     * @return
     */
    String getMsg();
}
