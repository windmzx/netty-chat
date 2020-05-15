package com.mzx.chatserver.netty.handler;


import com.mzx.chatcommon.Friend;
import com.mzx.chatcommon.Group;
import com.mzx.chatcommon.LoginPackage;
import com.mzx.chatcommon.LoginResponse;
import com.mzx.chatserver.dao.UserDao;
import com.mzx.chatserver.netty.util.Session;
import com.mzx.chatserver.netty.util.SessionUtil;
import com.mzx.chatserver.service.FriendService;
import com.mzx.chatserver.service.UserService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@ChannelHandler.Sharable
public class LoginHandler extends SimpleChannelInboundHandler<LoginPackage> {

    @Autowired
    UserService userService;

    @Autowired
    FriendService friendService;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginPackage msg) throws Exception {
        String username = msg.getUsername();
        String password = msg.getPassword();
        String userId = userService.vaildLogin(username, password);
        if (!userId.equals("-1")) {
            log.warn("登陆成功");
            LoginResponse response = new LoginResponse();

            List<UserDao> friendDaos = friendService.getUserFriends(userId);
            List<Friend> friends = new ArrayList<>();

            friendDaos.forEach(userDao -> {
                friends.add(new Friend(userDao.getId(), userDao.getUsername()));
            });
            response.setFriendList(friends);
            List<Group> groups = new ArrayList<>();
            groups.add(new Group("1234", Arrays.asList("123", "124")));
            response.setGroups(groups);
            response.setSuccess(true);
            response.setYourName(username);

            /**
             * 绑定session
             *
             */
            SessionUtil.bindSession(new Session(userId, msg.getUsername()), ctx.channel());

            ctx.writeAndFlush(response);
        } else {
            LoginResponse response = new LoginResponse();
            response.setSuccess(false);
            ctx.writeAndFlush(response);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接丢失");
        SessionUtil.unBindSession(ctx.channel());
    }
}
