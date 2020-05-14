package com.mzx.chatserver.netty.handler;

import com.mzx.chatcommon.GroupMessageRequest;
import com.mzx.chatcommon.GroupMessageResponse;
import com.mzx.chatserver.netty.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
public class GroupMessageHandler extends SimpleChannelInboundHandler<GroupMessageRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequest msg) throws Exception {
        GroupMessageResponse response = new GroupMessageResponse();
        response.setFromUserId(msg.getFromUserId());
        response.setFromUserName(msg.getFromUserName());
        response.setTargetGroupId(msg.getTargetGroupId());
        response.setMessage(msg.getMessage());

        // 2. 拿到群聊对应的 channelGroup，写到每个客户端
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(msg.getTargetGroupId());
        channelGroup.writeAndFlush(response);


    }
}
