package com.jerryCat.net;

/**
 * @author ivanzhu
 */
public interface JerryServlet {

    /**
     * 处理Http的get请求
     *
     * @param request  请求内容
     * @param response 响应内容
     * @throws Exception 异常
     */
    void doGet(JerryRequest request, JerryResponse response) throws Exception;

    /**
     * 处理Http的post请求
     *
     * @param request  请求内容
     * @param response 响应内容
     * @throws Exception 异常
     */
    void doPost(JerryRequest request, JerryResponse response) throws Exception;
}
