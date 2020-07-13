package com.zps.imchat.jsonbean;

import com.zps.imchat.bean.Group;
import com.zps.imchat.bean.MyFz;
import com.zps.imchat.bean.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :zps
 * @desc: 个人信息Json Bean
 */
public class MineJson {
   private User mine;
   private List<MyFz> friend=new ArrayList<>();
   private List<Group> group = new ArrayList<>();

    @Override
    public String toString() {
        return "MineJson{" +
                "mine=" + mine +
                ", friend=" + friend +
                ", group=" + group +
                '}';
    }

    public User getMine() {
        return mine;
    }

    public void setMine(User mine) {
        this.mine = mine;
    }

    public List<MyFz> getFriend() {
        return friend;
    }

    public void setFriend(List<MyFz> friend) {
        this.friend = friend;
    }

    public List<Group> getGroup() {
        return group;
    }

    public void setGroup(List<Group> group) {
        this.group = group;
    }
}
