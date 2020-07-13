package com.zps.imchat.mapper;

import com.zps.imchat.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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


}
