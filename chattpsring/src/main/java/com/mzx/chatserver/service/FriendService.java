package com.mzx.chatserver.service;

import com.mzx.chatserver.dao.FriendDao;
import com.mzx.chatserver.dao.UserDao;
import com.mzx.chatserver.repository.FriendRepostory;
import com.mzx.chatserver.repository.UserRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendService {

    @Autowired
    FriendRepostory friendRepostory;
    @Autowired
    UserRepostory userRepostory;

    public List<UserDao> getUserFriends(String userId) {
        List<FriendDao> friendDaos = friendRepostory.findFriendDaosByUserId(userId);
        List<UserDao> friends = new ArrayList<>();
        friendDaos.forEach(friendDao -> {
            UserDao userDao = userRepostory.findById(friendDao.getFirendId());
            friends.add(userDao);
        });
        return friends;
    }
}
