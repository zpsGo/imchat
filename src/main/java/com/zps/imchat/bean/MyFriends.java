package com.zps.imchat.bean;
/**
 * @author :zps
 * @desc:我的好友
 */
public class MyFriends {
    private Long myFriendId;

    private Long myFzId;

    private Long userId;

    private String nickname;

    private Long statu;

    public Long getStatu() {
        return statu;
    }

    public void setStatu(Long statu) {
        this.statu = statu;
    }

    public Long getMyFriendId() {
        return myFriendId;
    }

    public void setMyFriendId(Long myFriendId) {
        this.myFriendId = myFriendId;
    }

    public Long getMyFzId() {
        return myFzId;
    }

    public void setMyFzId(Long myFzId) {
        this.myFzId = myFzId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "MyFriends{" +
                "myFriendId=" + myFriendId +
                ", myFzId=" + myFzId +
                ", userId=" + userId +
                ", nickname='" + nickname + '\'' +
                ", statu=" + statu +
                '}';
    }
}
