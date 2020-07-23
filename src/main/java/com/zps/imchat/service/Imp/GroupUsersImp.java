package com.zps.imchat.service.Imp;

import com.zps.imchat.bean.User;
import com.zps.imchat.mapper.GroupUsersDao;
import com.zps.imchat.service.GroupUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author :zps
 * @desc: 群员信息服务层
 */
@Service
public class GroupUsersImp implements GroupUserService {

    @Autowired
    GroupUsersDao groupUsersDao;

    @Override
    public List<User> findGropUsers(Long groupid) {
        return groupUsersDao.findGropUsers(groupid);
    }

    @Override
    public List<Long> findGroupUsersId(Long groupId) {
        return groupUsersDao.findGroupUsersId(groupId);
    }

    @Override
    public void addGroupUser(long groupid, long friendid) {
        groupUsersDao.addGroupUser(groupid , friendid);
    }

    @Override
    public void updateGroupUser(long groupid, long friendid) {
        groupUsersDao.updateGroupUser(groupid , friendid);
    }

    @Override
    public void deleteGroupUser(long groupid, long friendid) {
        groupUsersDao.deleteGroupUser(groupid , friendid);
    }
}
