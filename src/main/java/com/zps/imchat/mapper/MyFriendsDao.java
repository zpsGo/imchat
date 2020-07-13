package com.zps.imchat.mapper;

import com.zps.imchat.bean.MyFz;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author :zps
 * @desc: 好友信息持久层接口
 */
@Mapper
public interface MyFriendsDao {
    MyFz findMyFz(long fz_id);
}
