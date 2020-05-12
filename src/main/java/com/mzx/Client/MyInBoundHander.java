package com.mzx.Client;

import com.mzx.Client.model.ClientModel;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyInBoundHander extends SimpleChannelInboundHandler<MessageResponse> {
    ClientModel clientModel = ClientModel.getInstance();


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponse msg) throws Exception {
        System.out.println("客户端回复" + msg.getMessage()  );
        clientModel.handleMessage(msg.getMessage());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("与服务器建立连接成功");
    }

}
