package com.mzx.chatcommon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse extends Packet {
    boolean isSuccess;
    private List<Friend> friendList;
    private List<Group> groups;
    private String yourName;
    @Override
    public Byte getCommond() {
        return Command.LOGIN_RESPONSE;
    }
}
