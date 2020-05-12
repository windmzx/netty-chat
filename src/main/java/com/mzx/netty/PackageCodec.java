package com.mzx.netty;

import com.mzx.netty.type.LoginPackage;

import java.nio.ByteBuffer;


public class PackageCodec {
    private static final int MAGIC_NUMBER = 0x12345678;

    public byte[] encode(Packet pack) {
        byte[] byte_2 = Serializer.DEFAULT.serializer(pack);

        ByteBuffer byteBuffer = ByteBuffer.allocate(11);
        byteBuffer.putInt(MAGIC_NUMBER);
        byteBuffer.put(pack.getVersion());
        byteBuffer.put(Serializer.DEFAULT.getSerizlizerAlg());
        byteBuffer.put(pack.getCommond());
        byteBuffer.putInt(byte_2.length);
        byte[] byte_1 = byteBuffer.array();

        byte[] byte_3 = new byte[byte_1.length + byte_2.length];
        System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
        System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
        return byte_3;
    }

    public static void main(String[] args) {
        LoginPackage a = new LoginPackage();
        a.setVersion((byte) 1);
        a.setVersion((byte) 1);
        a.setUsername("123");
        a.setPassword("456");
        PackageCodec codec = new PackageCodec();
        byte[] encode = codec.encode(a);
        System.out.println(encode);

    }


}
