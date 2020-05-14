package com.mzx.Client;

import com.mzx.Client.model.ClientModel;
import com.mzx.chatcommon.GroupMessageResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class CroupMessageResponseHandler extends SimpleChannelInboundHandler<GroupMessageResponse> {
    ClientModel clientModel = ClientModel.getInstance();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageResponse msg) throws Exception {
        String msgFrom = msg.getFromUserId();
//        是谁发来的
        clientModel.handleGroupMessage(msg);
    }
}
