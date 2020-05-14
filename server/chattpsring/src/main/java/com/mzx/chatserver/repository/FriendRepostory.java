package com.mzx.chatserver.repository;

import com.mzx.chatserver.dao.FriendDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepostory extends JpaRepository<FriendDao, Integer> {
    List<FriendDao> findFriendDaosByUserId(String UserId);
}
