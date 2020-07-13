package com.zps.imchat.bean;
/**
 * @author :zps
 * @desc: 群成员
 */
public class GroupUsers {
    private Long groupUserId;

    private Long userId;

    private Long groupId;

    private String nickname;

    public Long getGroupUserId() {
        return groupUserId;
    }

    public void setGroupUserId(Long groupUserId) {
        this.groupUserId = groupUserId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "GroupUsersDao{" +
                "groupUserId=" + groupUserId +
                ", userId=" + userId +
                ", groupId=" + groupId +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
