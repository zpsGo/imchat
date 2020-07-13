package com.zps.imchat.service.lmp;

import com.zps.imchat.bean.User;
import com.zps.imchat.mapper.UserMapper;
import com.zps.imchat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: zps
 **/
@Service
public class UserServiceImp implements UserService {

    @Autowired
    UserMapper userMapper;

    //用户登录
    @Override
    public User login(Long id, String pass) {
        return userMapper.login(id , pass);
    }

}
