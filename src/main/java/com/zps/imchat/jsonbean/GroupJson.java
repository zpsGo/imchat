package com.zps.imchat.jsonbean;


import com.zps.imchat.bean.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :zps
 * @desc: 群 Json Bean
 */
public class GroupJson {

    private List<User> list = new ArrayList<>();

    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "GroupJson{" +
                "list=" + list +
                '}';
    }
}
