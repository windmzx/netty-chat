package com.mzx.Dao;

import com.mzx.bean.ServerUser;

import java.sql.SQLException;

public class DBTest {
    public static void main(String[] args){
        ServerUser p = new ServerUser(3,"liming","student" );
        UserDaoImpl peronDao = UserDaoImpl.getInstance();
        try {
            peronDao.add(p);
            System.out.println("success");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
