package com.zps.imchat;

import com.zps.imchat.mapper.UserDao;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * @author: zps
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ImchatApplication.class)
public class TestMybatis {

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Test
    public void testStatement() throws IOException {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserDao userDao = session.getMapper(UserDao.class);
            userDao.getUserId();
        } finally {
            session.close();
        }
    }

}
