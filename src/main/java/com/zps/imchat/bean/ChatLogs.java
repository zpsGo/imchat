package com.zps.imchat.bean;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.Date;

/**
 * @author :zps
 * @desc: 聊天历史实体
 */
@Data
public class ChatLogs {
    private Long chatlogid;

    @SerializedName("from")
    private Long fromid;

    @SerializedName("to")
    private Long toid;

    private String content;

    private Date sendtime;

    @SerializedName("type")
    private String typ;

    private Integer status;

}

