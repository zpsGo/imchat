package com.zps.imchat.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author :zps
 * @desc:分组朋友信息实体
 */
public class MyFz implements Serializable {

    @SerializedName("id")
    private Long fzId;

    @SerializedName("groupname")
    private String fzGroupname;

    @SerializedName("list")
    private List<User> users=new ArrayList<>();

    public Long getFzId() {
        return fzId;
    }

    public void setFzId(Long fzId) {
        this.fzId = fzId;
    }

    public String getFzGroupname() {
        return fzGroupname;
    }

    public void setFzGroupname(String fzGroupname) {
        this.fzGroupname = fzGroupname;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "MyFz{" +
                "fzId=" + fzId +
                ", fzGroupname='" + fzGroupname + '\'' +
                ", users=" + users +
                '}';
    }
}
