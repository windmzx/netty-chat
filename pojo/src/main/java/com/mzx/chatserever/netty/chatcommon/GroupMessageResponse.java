package com.mzx.chatserever.netty.chatcommon;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class GroupMessageResponse extends Packet {
    @Override
    public Byte getCommond() {
        return Command.GROUP_MESSAGE_RESPONSE;

    }

    private String message;
    private String fromUserId;
    private String fromUserName;
    private String targetGroupId;
}
