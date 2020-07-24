package com.zps.imchat.bean;

import lombok.Data;

/**
 * @author :zps
 * @desc: 群成员
 */
@Data
public class GroupUsers {
    private Long groupUserId;

    private Long userId;

    private Long groupId;

    private String nickname;

}
