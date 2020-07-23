package com.zps.imchat.service;


import com.zps.imchat.bean.User;
import java.util.List;

/**
 * @author :zps
 * @desc: 群员信息持久层接口
 */
public interface GroupUserService {

    List<User> findGropUsers(Long groupid);

    List<Long> findGroupUsersId(Long groupId);

    void addGroupUser(long groupid, long friendid);

    void updateGroupUser(long groupid, long friendid);

    void deleteGroupUser(long groupid, long friendid);
}
