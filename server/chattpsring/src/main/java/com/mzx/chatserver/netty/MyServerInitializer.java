package com.mzx.chatserver.netty;

import com.mzx.chatcommon.PackageDecoder;
import com.mzx.chatcommon.PackageEncoder;
import com.mzx.chatcommon.PacketCodeC;
import com.mzx.chatserver.netty.handler.*;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    MessageCodec messageCodec;

    @Autowired
    LoginHandler loginHandler;

    @Autowired
    AuthHandler authHandler;

    @Autowired
    IMHandler imHandler;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //长度拆包，解决粘包问题
//        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 7, 4));
//        pipeline.addLast(new LineBasedFrameDecoder(Integer.MAX_VALUE));
//        pipeline.addLast(new LengthFieldPrepender(4));
//        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
//        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));

        pipeline.addLast(new LengthDecoder());
        pipeline.addLast(messageCodec);
        pipeline.addLast(loginHandler);
        pipeline.addLast(authHandler);
        pipeline.addLast(imHandler);
    }
}
