package com.mzx.chatserever.netty.handler;





public class Session {
    private String userName;
    private String userId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Session(String userName, String userId) {
        this.userName = userName;
        this.userId = userId;
    }
}
