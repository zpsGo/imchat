package com.zps.imchat.bean;

import lombok.Data;

/**
 * @author :zps
 * @desc:我的好友
 */
@Data
public class MyFriends {
    private Long myFriendId;

    private Long myFzId;

    private Long userId;

    private String nickname;

    private Integer statu;

}
