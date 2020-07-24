package com.zps.imchat.jsonbean;


import com.zps.imchat.bean.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :zps
 * @desc: 群 Json Bean
 */
@Data
public class GroupJson {

    private List<User> list = new ArrayList<>();

    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
    }

}
