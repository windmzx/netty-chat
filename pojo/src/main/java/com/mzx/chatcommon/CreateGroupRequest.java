package com.mzx.chatcommon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateGroupRequest extends Packet {
    @Override
    public Byte getCommond() {
        return Command.CREATE_GROUP;
    }

    private List<String> groupUsers;
    private String groupCreater;
}
