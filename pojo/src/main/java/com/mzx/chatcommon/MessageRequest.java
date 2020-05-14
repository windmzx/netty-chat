package com.mzx.chatcommon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest extends Packet{
    String fromUserId;
    String fromUserName;
    String message;
    String targetUserId;

    @Override
    public Byte getCommond() {
        return Command.MESSAGE_REQUEST;
    }
}
