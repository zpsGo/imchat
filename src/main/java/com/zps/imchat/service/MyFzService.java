package com.zps.imchat.service;

import com.zps.imchat.bean.MyFriends;
import org.apache.ibatis.annotations.Param;

/**
 * @author :zps
 * @desc: 我的分组持久层接口
 */
public interface MyFzService {

    //添加好友
    Long addFriend(MyFriends myFriends);

    //移除好友
    void deleteFriend(@Param("myFzid") Long myFzid , @Param("userId") Long userId);

}
