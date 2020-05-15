package com.mzx.chatserver.netty.util;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionUtil {
    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();
    private static final Map<String, String> userGroupId = new ConcurrentHashMap<>();
    private static final Map<String, ChannelGroup> groupIdChannelGroupMap = new ConcurrentHashMap<>();

    public static void bindSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getUserId(), channel);

        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            Session session = getSession(channel);
            userIdChannelMap.remove(session.getUserId());
            //完成 需要在群组关系中删除线程
            String groupId = userGroupId.get(session.getUserId());
            groupIdChannelGroupMap.get(groupId).remove(channel);

            channel.attr(Attributes.SESSION).set(null);
            System.out.println(session + " 退出登录!");

        }
    }

    public static boolean hasLogin(Channel channel) {

        return getSession(channel) != null;
    }

    public static Session getSession(Channel channel) {

        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(String userId) {

        return userIdChannelMap.get(userId);
    }

    public static void bindChannelGroup(Channel channel, String groupId, ChannelGroup channelGroup) {
        groupIdChannelGroupMap.put(groupId, channelGroup);
        String userId = getSession(channel).getUserId();
        userGroupId.put(userId, groupId);
    }

    public static ChannelGroup getChannelGroup(String groupId) {
        return groupIdChannelGroupMap.get(groupId);
    }
}