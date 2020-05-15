package com.mzx.chatserver.netty.handler;

import com.mzx.chatserver.netty.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.stereotype.Component;

@ChannelHandler.Sharable
@Component
public class AuthHandler extends ChannelInboundHandlerAdapter {




    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!SessionUtil.hasLogin(ctx.channel())) {
            ctx.channel().close();
        } else {
        //            如果登陆了 就移除登陆信息
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("已经登陆 移除登陆校检");
    }
}
