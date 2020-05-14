package com.mzx.chatserver.service;

import com.mzx.chatserver.dao.UserDao;
import com.mzx.chatserver.repository.UserRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepostory userRepostory;

    /**
     * 成功返回用户id 失败返回-1
     *
     * @param username
     * @param password
     * @return -1失败
     */

    public String vaildLogin(String username, String password) {
        UserDao user = userRepostory.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user.getId();
        } else {
            return "-1";
        }
    }

}
