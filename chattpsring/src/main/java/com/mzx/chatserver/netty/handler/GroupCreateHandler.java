package com.mzx.chatserver.netty.handler;


import com.mzx.chatcommon.CreateGroupRequest;
import com.mzx.chatcommon.CreateGroupResponse;
import com.mzx.chatserver.netty.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@ChannelHandler.Sharable
public class GroupCreateHandler extends SimpleChannelInboundHandler<CreateGroupRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequest msg) throws Exception {
        List<String> userlist = msg.getGroupUsers();
        List<String> userlist2 = new ArrayList<>();
        ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());
        for (String userId : userlist) {
            Channel channel = SessionUtil.getChannel(userId);
            if (channel != null && channel != ctx.channel()) {
                channelGroup.add(channel);
                userlist2.add(SessionUtil.getSession(channel).getUserName());
            }
        }
        //需要将自己加入群聊
        channelGroup.add(ctx.channel());
        userlist2.add(SessionUtil.getSession(ctx.channel()).getUserName());
        CreateGroupResponse response = new CreateGroupResponse();
        response.setGroupCreater(msg.getGroupCreater());
        response.setGroupUsers(userlist2);

        Random random = new Random();
        int i = random.nextInt(1000);
        String groupId = "[group]" + i;
        while (SessionUtil.getChannelGroup(groupId) != null) {
            i = random.nextInt(1000);
            groupId = "[group]" + i;
        }
        SessionUtil.bindChannelGroup(groupId, channelGroup);
        response.setGroupId(groupId);
        channelGroup.writeAndFlush(response);
    }
}
