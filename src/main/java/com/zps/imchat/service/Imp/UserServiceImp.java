package com.zps.imchat.service.Imp;

import com.zps.imchat.bean.Group;
import com.zps.imchat.bean.User;
import com.zps.imchat.jsonbean.GroupJson;
import com.zps.imchat.jsonbean.LayimJson;
import com.zps.imchat.jsonbean.MermbersJson;
import com.zps.imchat.jsonbean.MineJson;
import com.zps.imchat.mapper.*;
import com.zps.imchat.bean.MyFz;
import com.zps.imchat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :zps
 * @desc: 个人信息持服务层
 */
@Service
public class UserServiceImp implements UserService {

    @Autowired
    UserDao userDao;
    @Autowired
    UserFzDao userFzDao;
    @Autowired
    MyFriendsDao myFriendsDao;
    @Autowired
    GroupDao groupDao;

    @Autowired
    GroupUsersDao groupUsersDao;

    //获取用户面板的信息
    @Override
    public LayimJson getMineInfo(Long userId) {
        LayimJson layimJson = new LayimJson();
        //初始化的个人信息包括我的分组和我的群聊信息
        MineJson mineJson = new MineJson();
        List<MyFz> friends = new ArrayList<>();
        List<Group> groups = new ArrayList<>();

        //查找我的分组id
        List<Long> fzIds = userFzDao.findFzId(userId);
        //根据分组id查找好友信息
        for (int i = 0; i < fzIds.size(); i++) {
            friends.add(myFriendsDao.findMyFz((fzIds.get(i))));
        }
        //我所拥有的群信息
//        groups = groupDao.findGroups(userId);
        groups.addAll(groupDao.findGroupsByUserId(userId));
//        groups.add();
        //第二层
        mineJson.setMine(userDao.findUser(userId));
        mineJson.setFriend(friends);
        mineJson.setGroup(groups);
        //第一层
        layimJson.setCode(0);
        layimJson.setMsg("成功");
        layimJson.setMineResult(mineJson);

        return layimJson;
    }

    //获取群成员信息
    @Override
    public MermbersJson getMermbers(Long gruopid) {
        MermbersJson mermbersJson = new MermbersJson();
        GroupJson groupJson = new GroupJson();
        //获取群主信息
        User user = groupUsersDao.getUserByGroupId(gruopid);
        if (user == null)
            return null;
        user.setUsername("【群主】" + user.getUsername());
        //添加成员信息，包括群主
        groupJson.getList().add(user);
        groupJson.getList().addAll(groupUsersDao.findGropUsers(gruopid));

        //第一层
        mermbersJson.setCode(0);
        mermbersJson.setMsg("成功！");
        mermbersJson.setData(groupJson);
        return mermbersJson;
    }

    //登录验证
    @Override
    public User login(User user) {
        return userDao.login(user);
    }

    @Override
    public User findUser(long id) {
        return userDao.findUser(id);
    }

    @Override
    public void updateUserStatus(Long userId, String status) {
        userDao.updateUserStatus(userId, status);
    }

    @Override
    public List<Long> getUserId() {
        return userDao.getUserId();
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public boolean saveUser(User user) {
        return userDao.saveUser(user) == 1;
    }

    @Override
    public User getUserInfo(Long userId) {
        return userDao.getUserInfo(userId);
    }

    @Override
    public void updateUserInfo(User user) {
        userDao.updateUserInfo(user);
    }

}








