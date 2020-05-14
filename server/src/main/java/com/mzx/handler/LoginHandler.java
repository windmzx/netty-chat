package com.mzx.handler;

import com.mzx.pojo.LoginPackage;
import com.mzx.pojo.MessageResponse;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class LoginHandler extends SimpleChannelInboundHandler<LoginPackage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginPackage msg) throws Exception {
        LoginPackage loginPackage = (LoginPackage) msg;

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setTo("1");
        messageResponse.setFrom("2");
        messageResponse.setMessage("12312");
        ctx.channel().writeAndFlush(messageResponse);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接丢失");
    }
}
