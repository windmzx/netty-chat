package com.mzx.netty.type;


import com.mzx.netty.Command;
import com.mzx.netty.Package;

public class LoginPackage extends Package {
    @Override
    public Byte getCommond() {
        return Command.LOGIN;
    }

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
