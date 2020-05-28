package com.mzx.chatserver.netty.handler;

import com.mzx.chatcommon.Command;
import com.mzx.chatcommon.Packet;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@ChannelHandler.Sharable
@Component
public class IMHandler extends SimpleChannelInboundHandler<Packet> {

    private Map<Byte, SimpleChannelInboundHandler> handlerMap;

    @Autowired
    GroupCreateHandler groupCreateHandler;

    @Autowired
    GroupMessageHandler groupMessageHandler;

    @Autowired
    MessageHandler messageHandler;


    @PostConstruct
    private void init() {
        handlerMap = new HashMap<>();
        handlerMap.put(Command.CREATE_GROUP, groupCreateHandler);
        handlerMap.put(Command.GROUP_MESSAGE, groupMessageHandler);
        handlerMap.put(Command.MESSAGE_REQUEST, messageHandler);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        handlerMap.get(msg.getCommond()).channelRead(ctx, msg);
    }

}
