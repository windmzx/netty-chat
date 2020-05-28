package com.mzx.chatserver.netty.handler;


import com.mzx.chatcommon.MessageRequest;
import com.mzx.chatcommon.MessageResponse;
import com.mzx.chatserver.netty.util.Session;
import com.mzx.chatserver.netty.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.stereotype.Component;

@Component
public class MessageHandler extends SimpleChannelInboundHandler<MessageRequest> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequest msgreq) throws Exception {
        System.out.println("客户端发送的消息是" + msgreq.getMessage());


        Session session = SessionUtil.getSession(ctx.channel());

        /**
         * 构造转发数据包
         */
        MessageResponse messageResponsePacket = new MessageResponse();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUsername(session.getUserName());
        messageResponsePacket.setMessage(msgreq.getMessage());
        messageResponsePacket.setTargetUserId(msgreq.getTargetUserId());

        /**
         * 获取目的用户的channel
         */
        Channel toUserChannel = SessionUtil.getChannel(msgreq.getTargetUserId());

        if (null != toUserChannel) {
            toUserChannel.writeAndFlush(messageResponsePacket);
//            转发给来源用户显示消息
//            TODO 显示已读未读 不在线的话告知发送用户
            ctx.writeAndFlush(messageResponsePacket);
        } else {
            //目标客户不在线 应该持久化消息
            System.err.println("[" + msgreq.getTargetUserId() + "] 不在线，发送失败!");

            ctx.channel().writeAndFlush(messageResponsePacket);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
        super.channelInactive(ctx);
    }
}
