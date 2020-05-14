package com.mzx.chatcommon;

import io.netty.buffer.ByteBuf;

public class PacketCodeC {
    private static final int MAGIC_NUMBER = 0x12345678;
    public static final PacketCodeC INSTANCE = new PacketCodeC();

    private PacketCodeC() {
    }

    public void encode(Packet packet, ByteBuf out) {
        // 1. 创建 ByteBuf 对象
        // 2. 序列化 Java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 3. 实际编码过程
        out.writeInt(MAGIC_NUMBER);
        out.writeByte(packet.getVersion());
        out.writeByte(Serializer.DEFAULT.getSerizlizerAlg());
        out.writeByte(packet.getCommond());
        out.writeInt(bytes.length);
        out.writeBytes(bytes);

    }

    public Packet decode(ByteBuf byteBuf) {
        // 跳过 magic number
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 序列化算法标识
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
            return serializer.deserialize(requestType, bytes);
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
            case Command.LOGIN_RESPONSE:
                return LoginResponse.class;


            case Command.MESSAGE_REQUEST:
                return MessageRequest.class;
            case Command.MESSAGE_RESPONSE:
                return MessageResponse.class;


            case Command.GROUP_MESSAGE:
                return GroupMessageRequest.class;
            case Command.GROUP_MESSAGE_RESPONSE:
                return GroupMessageResponse.class;

            case Command.CREATE_GROUP:
                return CreateGroupRequest.class;
            case Command.CREATE_GROUP_RESPONSE:
                return CreateGroupResponse.class;

            default:
                return Packet.class;
        }

    }
}
