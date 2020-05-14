package com.mzx.handler;

import com.mzx.PacketCodeC;
import com.mzx.pojo.LoginPackage;
import com.mzx.pojo.MessagePackage;
import com.mzx.pojo.MessageResponse;
import com.mzx.pojo.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class MessageHandler extends SimpleChannelInboundHandler<MessagePackage> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessagePackage msg) throws Exception {
        System.out.println("客户端发送的消息是" + msg.getContent());

        MessageResponse respone = new MessageResponse();
        respone.setMessage(msg.getContent());
        respone.setFrom("you");
        respone.setTo("owner");
//            ctx.writeAndFlush("recived message" + "\n");

        ctx.channel().writeAndFlush(respone);
    }
}
