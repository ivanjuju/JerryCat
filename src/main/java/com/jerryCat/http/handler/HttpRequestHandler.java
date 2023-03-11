package com.jerryCat.http.handler;


import com.jerryCat.http.model.HttpJerryRequest;
import com.jerryCat.http.model.HttpJerryResponse;
import com.jerryCat.net.JerryServlet;
import com.jerryCat.servlet.ServletContext;
import com.jerryCat.servlet.bean.DefaultServlet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import java.util.Optional;

/**
 * @author ivanzhu
 */
public class HttpRequestHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // 连接
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        // 断开连接
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 读操作
        if (msg instanceof HttpRequest) {
            HttpRequest httpRequest = (HttpRequest) msg;
            String uri = httpRequest.getUri();
            int indexOf = uri.indexOf("?");
            int endIndex = indexOf == -1 ? uri.length() : indexOf;
            String servletName = uri.substring(uri.lastIndexOf("/") + 1, endIndex);
            JerryServlet jerryServlet = Optional.ofNullable(ServletContext.doGetServlet(servletName)).orElse(new DefaultServlet());

            HttpJerryRequest req = new HttpJerryRequest(httpRequest);
            HttpJerryResponse res = new HttpJerryResponse(req, ctx);
            if (HttpMethod.GET.name().equals(req.getMethod())) {
                jerryServlet.doGet(req, res);
            } else if (HttpMethod.POST.name().equals(req.getMethod())) {
                jerryServlet.doPost(req, res);
            }
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 读完成
        ctx.writeAndFlush("啊哈哈哈");
        ctx.close();
    }
}
