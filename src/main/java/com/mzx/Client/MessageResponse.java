package com.mzx.Client;

import com.mzx.netty.Command;
import com.mzx.netty.Packet;

public class MessageResponse extends Packet {
    @Override
    public Byte getCommond() {
        return Command.GROUP_MESSAGE;
    }

    private String message;
    private String from;
    private String to;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
