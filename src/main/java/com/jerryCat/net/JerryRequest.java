package com.jerryCat.net;

import java.util.List;
import java.util.Map;

/**
 * @author ivanzhu
 */
public interface JerryRequest {

    /**
     * 获取 uri
     * @return uri
     */
    String getUri();

    /**
     * 获取 请求路径
     * @return 请求路径
     */
    String getPath();

    /**
     * 获取请求类型
     * @return POST、GET...
     */
    String getMethod();

    /**
     * 获取所有请求参数
     * @return 请求参数
     */
    Map<String, List<String>> getParameters();

    /**
     * 获取指定名称的请求参数
     * @param name 参数名称
     * @return 参数值
     */
    List<String> getParameters(String name);

    /**
     * 获取指定名称的请求参数的第一个值
     * @param name 参数名称
     * @return 参数值
     */
    String getParameter(String name);

    /**
     * 是否是长连接
     * @return true 是 false 否
     */
    boolean isKeepAlive();
}
