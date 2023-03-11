package com.jerryCat;

import com.jerryCat.config.ApplicationConfiguration;
import com.jerryCat.http.handler.HttpRequestHandler;
import com.jerryCat.servlet.ServletContext;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import java.net.InetSocketAddress;
import lombok.SneakyThrows;

/**
 * @author ivanzhu
 */
public class ApplicationStarter {

    @SneakyThrows
    public static void run(Class<?> applicationClass) {

        System.out.println("JerryCat开始启动");
        ApplicationConfiguration configuration = ApplicationConfiguration.initial(applicationClass);
        // 加载配置
        System.out.println("加载启动配置");
        // 缓存servlet
        ServletContext.cacheServlet(configuration.getBaseServletPackage(), applicationClass);
        System.out.println("缓存Servlet");
        // 启动项目
        NioEventLoopGroup bootGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup(200);
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
            .group(bootGroup, workGroup)
            .channel(NioServerSocketChannel.class)
            .childOption(ChannelOption.SO_KEEPALIVE, true)
            .childHandler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel channel) {
                    channel.pipeline()
                        .addLast(new HttpServerCodec())
                        .addLast(new HttpRequestHandler());
                }
            });
        ChannelFuture channelFuture = serverBootstrap.bind(new InetSocketAddress(configuration.getPort()));
        System.out.println(String.format("JerryCat已启动，端口号：%s", configuration.getPort()));
        channelFuture.channel().closeFuture().sync();
        bootGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
    }
}
