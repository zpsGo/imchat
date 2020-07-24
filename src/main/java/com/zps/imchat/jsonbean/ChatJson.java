package com.zps.imchat.jsonbean;

import lombok.Data;

/**
 * @author :zps
 * @desc:聊天消息Json Bean
 */
@Data
public class ChatJson {
    private String chatType;

    private String username;

    private String id;

    private String type;

    private String content;

    private String avatar;

    private String cid;

    private boolean mine = false;

    private String fromid;

    private Long timestamp;

    private String other;
}



