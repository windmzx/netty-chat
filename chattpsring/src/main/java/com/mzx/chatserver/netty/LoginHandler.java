package com.mzx.chatserver.netty;


import com.mzx.chatcommon.Friend;
import com.mzx.chatcommon.Group;
import com.mzx.chatcommon.LoginPackage;
import com.mzx.chatcommon.LoginResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginHandler extends SimpleChannelInboundHandler<LoginPackage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginPackage msg) throws Exception {

        String userId = msg.getUsername();
        SessionUtil.bindSession(new Session(userId, msg.getUsername()), ctx.channel());
        LoginResponse response = new LoginResponse();
        List<Friend> friends = new ArrayList<>();
        friends.add(new Friend("123", "张三"));
        friends.add(new Friend("124", "李四"));
        response.setFriendList(friends);
        List<Group> groups = new ArrayList<>();
        groups.add(new Group("123", Arrays.asList("123", "124")));
        response.setGroups(groups);
        response.setSuccess(true);
        ctx.writeAndFlush(response);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接丢失");
    }
}
