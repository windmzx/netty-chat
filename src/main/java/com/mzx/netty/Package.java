package com.mzx.netty;

public abstract class Package {
    private byte version = 1;

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public abstract Byte getCommond();
}
