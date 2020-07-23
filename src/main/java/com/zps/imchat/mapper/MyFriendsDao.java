package com.zps.imchat.mapper;

import com.zps.imchat.bean.MyFriends;
import com.zps.imchat.bean.MyFz;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author :zps
 * @desc: 好友信息持久层接口
 */
@Mapper
public interface MyFriendsDao {

    //根据分组id查询好友
    MyFz findMyFz(long fz_id);

    //添加好友
    Long addFriend(MyFriends myFriends);

    //移除好友
    void deleteFriend(@Param("myFzid") Long myFzid , @Param("userId") Long userId);

    void updateStaus(@Param("fzid") Long fzid , @Param("userid") Long userid);

}
