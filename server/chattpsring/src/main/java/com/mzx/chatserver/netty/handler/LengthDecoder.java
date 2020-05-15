package com.mzx.chatserver.netty.handler;

import com.mzx.chatcommon.PackageDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class LengthDecoder extends LengthFieldBasedFrameDecoder {
    private static final int LENGTH_OFFSET = 7;
    private static final int LENGTH_LENGHT = 4;

    public LengthDecoder() {
        super(Integer.MAX_VALUE, LENGTH_OFFSET, LENGTH_LENGHT);

    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if (in.getInt(in.readerIndex()) != PackageDecoder.MAGIC_NUMBER){
            ctx.channel().close();
//            异常协议
            System.out.println("异常协议");
        }
        return super.decode(ctx, in);
    }
}
