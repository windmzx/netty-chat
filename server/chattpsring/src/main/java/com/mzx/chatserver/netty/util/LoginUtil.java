package com.mzx.chatserver.netty.util;


import com.mzx.chatserver.netty.util.Attributes;
import com.mzx.chatserver.netty.util.Session;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

public class LoginUtil {
    public static boolean hasLogin(Channel channel) {
        Attribute<Session> attr = channel.attr(Attributes.SESSION);
        return attr.get() != null;
    }

    public static void  markLoged(Channel channel) {
        channel.attr(Attributes.SESSION);
    }
}
