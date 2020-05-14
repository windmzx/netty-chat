package com.mzx.chatserever.netty.handler;

import com.mzx.chatserever.netty.chatcommon.PackageDecoder;
import com.mzx.chatserever.netty.chatcommon.PackageEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //长度拆包，解决粘包问题
//        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 7, 4));


        pipeline.addLast(new LengthDecoder());
        pipeline.addLast(new PackageDecoder());
        pipeline.addLast(new PackageEncoder());
//        pipeline.addLast(new LineBasedFrameDecoder(Integer.MAX_VALUE));
////        pipeline.addLast(new LengthFieldPrepender(4));
//        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
//        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        pipeline.addLast(new LoginHandler());
        pipeline.addLast(AuthHandler.INSTANCE);
        pipeline.addLast(new MessageHandler());
    }
}
