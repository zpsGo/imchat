package com.zps.imchat.service;


import com.zps.imchat.bean.User;
import com.zps.imchat.jsonbean.LayimJson;
import com.zps.imchat.jsonbean.MermbersJson;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author :zps
 * @desc: 个人信息持久层接口
 */
public interface UserService {

    LayimJson getMineInfo(Long userId);

    MermbersJson getMermbers(Long gruopid);

    User login(User user);

    public User findUser(long id);

    void updateUserStatus(Long userId, String status);

    List<Long> getUserId();

    User getUserByEmail(@Param("email") String email);

    boolean saveUser(@Param("user") User user);

    User getUserInfo(@Param("userId") Long userId);

    void updateUserInfo(@Param("user") User user);

}
