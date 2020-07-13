package com.zps.imchat.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author :zps
 * @desc：群信息
 */
public class Group {
    @SerializedName("id")
    private Long group_id;
    private String groupname;
    private String avator;
    private String notice;
    private Long user_id;
    private Integer approve;

    public Long getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Long group_id) {
        this.group_id = group_id;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Integer getApprove() {
        return approve;
    }

    public void setApprove(Integer approve) {
        this.approve = approve;
    }

    @Override
    public String toString() {
        return "GroupDao{" +
                "group_id=" + group_id +
                ", groupname='" + groupname + '\'' +
                ", avator='" + avator + '\'' +
                ", notice='" + notice + '\'' +
                ", user_id=" + user_id +
                ", approve=" + approve +
                '}';
    }
}
