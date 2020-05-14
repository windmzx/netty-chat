package com.mzx.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyServerHandler extends SimpleChannelInboundHandler<String> {
    /**
     * ChannelHandlerContext ctx: 表示请求上下文信息。可用于获得channel，远程地址等
     * msg ：表示客户端发送来的消息
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " : " + msg);
        ctx.channel().writeAndFlush("你刚才的发言是" + msg + "\n");

    }

    //异常的捕获，一般出现异常，就把连接关闭
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}



