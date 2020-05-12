package com.mzx.netty;


import com.mzx.Client.LoginResponseHandler;
import com.mzx.Client.MessageResponseHandler;
import com.mzx.chatcommon.PackageDecoder;
import com.mzx.chatcommon.PackageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.logging.Logger;


public class NettyClient {
    private String clientName = "beyondwu";
    public static final int BUFFER_SIZE = 1024;
    public static final int HISTORY_LIST_SIZE = 30;
    Bootstrap bootstrap;
    private ChannelPipeline chanPipeline;
    private Channel socketChannel;

    private static Logger logger = Logger.getLogger(String.valueOf(NettyClient.class));

    private String ip;

    private int port;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    ChannelFuture future;

    private EventExecutorGroup bizGroup = null;

    public NettyClient() {
        bizGroup = new DefaultEventExecutorGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(1);
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.option(ChannelOption.SO_REUSEADDR, true);

            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {

                    ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 7, 4));
                    ch.pipeline().addLast(new PackageDecoder());
//                    ch.pipeline().addLast(new LoginResponseHandler());
//                    ch.pipeline().addLast(new MessageResponseHandler());
                    ch.pipeline().addLast(new LoginResponseHandler());
                    ch.pipeline().addLast(new MessageResponseHandler());
                    ch.pipeline().addLast(new PackageEncoder());
                }
            });

            // future = b.connect(ip, port);
            bootstrap = b;
            // logger.info("成功连接到 {}:{}", ip, port);
        } catch (Throwable t) {
//            logger.log("异常", t);
        }
    }

    public int connServer(String IP, Integer port) {
        // Start the client.
        ChannelFuture chanFuture;
        try {
            chanFuture = bootstrap.connect(IP, port).sync();
            socketChannel = chanFuture.channel();
            // Wait until the connection is closed.
            System.out.println("connect");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public void sendMsg(String msg) {
        String newMessage;
        //Send to Server
        ByteBuf sendMsg = Unpooled.buffer(BUFFER_SIZE);
        sendMsg.writeBytes(msg.getBytes());
        System.out.println("Going to Send: " + msg);
        socketChannel.writeAndFlush(sendMsg);
        newMessage = clientName + ": " + msg;
    }

    public void connect() {
        try {
            future = bootstrap.connect(ip, port).sync();
//            logger.log("成功连接到 {}:{}", ip, port);
        } catch (InterruptedException e) {
//            logger.debug("连接服务器异常", e);
        }
    }

    public void disConnect() {
        if (future != null) {
            future.channel().close();
            future = null;
        }
    }
}

