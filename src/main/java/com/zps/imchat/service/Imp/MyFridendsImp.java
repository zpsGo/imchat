package com.zps.imchat.service.Imp;

import com.zps.imchat.mapper.MyFriendsDao;
import com.zps.imchat.bean.MyFz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author :zps
 * @desc: 好友信息服务层
 */
@Service
public class MyFridendsImp implements MyFriendsDao {

    @Autowired
    MyFriendsDao myFriendsDao;

    @Override
    public MyFz findMyFz(long fz_id) {
        return myFriendsDao.findMyFz(fz_id);
    }
}
