package com.mzx.Client;

import com.mzx.Client.model.ClientModel;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class MyInBoundHander extends ChannelInboundHandlerAdapter {
    ClientModel clientModel = ClientModel.getInstance();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {

            System.out.println("000");
            ByteBuf byteBuffer = (ByteBuf) msg;
            byte[] barray = new byte[byteBuffer.readableBytes()];
//把数据从bytebuf转移到byte[]
            byteBuffer.getBytes(0, barray);
            //将byte[]转成字符串用于打印
            String str = new String(barray);
            clientModel.handleMessage(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("与服务器建立连接成功");
    }
}
