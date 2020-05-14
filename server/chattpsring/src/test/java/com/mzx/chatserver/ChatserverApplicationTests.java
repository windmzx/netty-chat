package com.mzx.chatserver;

import com.mzx.chatserver.dao.UserDao;
import com.mzx.chatserver.repository.UserRepostory;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class ChatserverApplicationTests {

    @Autowired
    UserRepostory userRepostory;

    @Before
    void beofre() {

    }

    @Test
    void query() {
        UserDao userDO = new UserDao();
        userDO.setId("123");
        userDO.setUsername("123");
        userDO.setPassword("456");

        userDO.setPassword("passWord");
        userRepostory.save(userDO);
        UserDao userDao = userRepostory.findByUsername("123");
        System.out.println(userDao);
    }


}
