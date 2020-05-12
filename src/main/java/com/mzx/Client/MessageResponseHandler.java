package com.mzx.Client;

import com.mzx.Client.model.ClientModel;

import com.mzx.chatcommon.MessageResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponse> {
    ClientModel clientModel = ClientModel.getInstance();


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponse msg) throws Exception {

        String msgFrom = msg.getFromUserId();
//        是谁发来的
        clientModel.handleMessage(msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("与服务器建立连接成功");
    }

}
