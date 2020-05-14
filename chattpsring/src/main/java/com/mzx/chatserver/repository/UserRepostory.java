package com.mzx.chatserver.repository;

import com.mzx.chatserver.dao.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepostory extends JpaRepository<UserDao, Integer> {
    UserDao findByUsername(String s);

    UserDao findById(String userid);
}
