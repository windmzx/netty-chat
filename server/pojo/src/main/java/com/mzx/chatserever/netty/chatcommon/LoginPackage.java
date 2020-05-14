package com.mzx.chatserever.netty.chatcommon;

import lombok.Data;

@Data
public class LoginPackage extends Packet {
    @Override
    public Byte getCommond() {
        return Command.LOGIN;
    }

    private String username;
    private String password;

}
