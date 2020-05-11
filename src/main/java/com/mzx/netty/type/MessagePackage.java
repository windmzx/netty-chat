package com.mzx.netty.type;

import com.mzx.netty.Command;
import com.mzx.netty.Package;

public class MessagePackage extends Package {
    @Override
    public Byte getCommond() {
        return Command.GROUP_MESSAGE;
    }

    private String content;
    private String from;
    private String to;
    private Long date;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

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

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }
}
