package com.mzx.chatserver.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;


@Component
@Slf4j
public class NettyServer {
    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup workGroup = new NioEventLoopGroup();
    private Channel channel;

    @Autowired
    MyServerInitializer myServerInitializer;

    public ChannelFuture start(String hostname, int port) {

        ChannelFuture future = null;
        try {

            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.option(ChannelOption.SO_REUSEADDR, true);
            bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(hostname, port))
                    .childHandler(myServerInitializer);
            future = bootstrap.bind().sync();
            channel = future.channel();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != future && future.isSuccess()) {
                log.info("netty start success");
            } else {
                log.error("netty start failed");
            }
        }
        return future;
    }

    public void destroy() {
        log.info("stop the nettyserver");
        if (channel != null) {
            channel.close();
        }
        workGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
        log.info("stop the nettyserver success");
    }
}