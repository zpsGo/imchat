package com.zps.imchat.service.Imp;

import com.zps.imchat.bean.MyFriends;
import com.zps.imchat.mapper.MyFriendsDao;
import com.zps.imchat.service.MyFzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author :zps
 * @desc: 我的分组服务层
 */
@Service
public class MyFzImp implements MyFzService {

    @Autowired
    MyFriendsDao myFriendsDao;

    @Override
    public Long addFriend(MyFriends myFriends) {
        return myFriendsDao.addFriend(myFriends);
    }

    @Override
    public void deleteFriend(Long myFzid, Long userId) {
        myFriendsDao.deleteFriend(myFzid , userId);
    }
}
