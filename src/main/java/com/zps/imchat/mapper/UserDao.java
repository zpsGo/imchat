package com.zps.imchat.mapper;

import com.zps.imchat.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author :zps
 * @desc: 个人信息持久层接口
 */
@Mapper
public interface UserDao {

    //登录
    User login(@Param("user") User user);

    //查找个人信息
    public User findUser(long id);

    void updateUserStatus(@Param("userId") Long userId, @Param("status") String status);

    //获取所用用户的id
    List<Long> getUserId();

    /**
     * @param email 邮箱地址
     * @return User
     * @Description: 通过邮箱获取用户
     */
    User getUserByEmail(@Param("email") String email);

    int saveUser(@Param("user") User user);

    User getUserInfo(@Param("userId") Long userId);

    void updateUserInfo(@Param("user") User user);
}
