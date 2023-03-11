package com.jerryCat.net;

/**
 * @author ivanzhu
 */
public interface JerryResponse {

    /**
     * 将数据写入到Channel中
     *
     * @param content 内容
     * @throws Exception 异常
     */
    void write(String content) throws Exception;
}
