package com.mzx.netty;

import com.mzx.Client.MessageResponse;
import com.mzx.netty.type.LoginPackage;
import com.mzx.netty.type.MessagePackage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class PacketCodeC {
    private static final int MAGIC_NUMBER = 0x12345678;
    public static final PacketCodeC INSTANCE = new PacketCodeC();

    private PacketCodeC() {
    }

    public void encode(Packet packet, ByteBuf byteBuf) {
        // 1. 创建 ByteBuf 对象
        // 2. 序列化 Java 对象
        byte[] bytes = Serializer.DEFAULT.serializer(packet);

        // 2. 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerizlizerAlg());
        byteBuf.writeByte(packet.getCommond());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

    }

    public Packet decode(ByteBuf byteBuf) {
//        int anInt = byteBuf.getInt(byteBuf.readerIndex());

        // 跳过 magic number
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 序列化算法
        byte serializeAlgorithm = byteBuf.readByte();

        // 指令
        byte command = byteBuf.readByte();

        // 数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserializer(requestType, bytes);
        }

        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {
        switch (serializeAlgorithm) {
            case (byte) 1:
                return new JSONSerializer();

            default:
                return new JSONSerializer();
        }
    }

    private Class<? extends Packet> getRequestType(byte command) {
        switch (command) {
            case Command.LOGIN:
                return LoginPackage.class;
            case Command.GROUP_MESSAGE:
                return MessagePackage.class;
            case Command.MESSAGE_RESPONSE:
                return MessageResponse.class;
            default:
                return MessagePackage.class;
        }

    }
}
