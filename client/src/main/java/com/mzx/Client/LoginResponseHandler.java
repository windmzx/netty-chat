package com.mzx.Client;

import com.mzx.Client.model.ClientModel;
import com.mzx.bean.ClientUser;
import com.mzx.chatcommon.Friend;
import com.mzx.chatcommon.Group;
import com.mzx.chatcommon.LoginResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponse> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponse msg) throws Exception {
        ClientModel model = ClientModel.getInstance();
        boolean loginsuccess = msg.isSuccess();
        if (loginsuccess) {
            List<Friend> friendList = msg.getFriendList();
            List<Group> groups = msg.getGroups();
            model.initChatList(friendList, groups, msg.getYourName());
        } else {
            System.out.println("登陆失败");
        }

    }
}
