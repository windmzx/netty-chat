package com.mzx.chatserever.netty.chatcommon;


import lombok.Data;

@Data
public class MessageResponse extends Packet {
    @Override
    public Byte getCommond() {
        return Command.MESSAGE_RESPONSE;
    }

    private String message;
    private String fromUserId;
    private String fromUsername;
    private String targetUserId;


}
