package com.zps.imchat.service;


import com.zps.imchat.bean.MyFz;;

/**
 * @author :zps
 * @desc: 好友信息持久层接口
 */
public interface MyFriendService {

    MyFz findMyFz(long fz_id);
}
