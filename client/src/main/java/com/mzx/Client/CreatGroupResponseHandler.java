package com.mzx.Client;

import com.mzx.Client.model.ClientModel;
import com.mzx.chatcommon.CreateGroupResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

public class CreatGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponse> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponse msg) throws Exception {
        ClientModel model = ClientModel.getInstance();
        String groupId = msg.getGroupId();
        String groupCreater = msg.getGroupCreater();
        List<String> groupUsers = msg.getGroupUsers();

        model.joinGroup(msg);
    }
}
