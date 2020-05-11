package com.mzx.pojo;

public class MessagePackage extends Packet {
    @Override
    public Byte getCommond() {
        return (byte) 2;
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
