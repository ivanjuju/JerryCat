package com.jerryCat.http.model;

import com.jerryCat.net.JerryRequest;
import com.jerryCat.net.JerryResponse;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.internal.StringUtil;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

/**
 * @author ivanzhu
 */
@AllArgsConstructor
public class HttpJerryResponse implements JerryResponse {

    /**
     * 请求
     */
    private JerryRequest jerryRequest;
    /**
     * 请求上下文
     */
    private ChannelHandlerContext ctx;

    @SneakyThrows
    public void write(String content){
        if (StringUtil.isNullOrEmpty(content)) {
            return;
        }
        FullHttpResponse response =
            new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
                // 根据响应体内容大小为response对象分配存储空间
                Unpooled.wrappedBuffer(content.getBytes("UTF-8"))
            );
        HttpHeaders headers = response.headers();
        headers.set(HttpHeaderNames.CONTENT_TYPE, "text/json");
        headers.set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        headers.set(HttpHeaderNames.EXPIRES, 0);
        if (jerryRequest.isKeepAlive()) {
            headers.set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }
        ctx.writeAndFlush(response);
    }
}
