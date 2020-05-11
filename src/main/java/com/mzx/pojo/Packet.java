package com.mzx.pojo;

public abstract class Packet {
    private byte version = 1;

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public abstract Byte getCommond();
}
