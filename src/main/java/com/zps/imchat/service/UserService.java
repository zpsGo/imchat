package com.zps.imchat.service;

import com.zps.imchat.bean.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author: zps
 * @desc: 用户服务接口
 **/
public interface UserService {

    User login(@Param("id") Long id , @Param("pass") String pass);

}
