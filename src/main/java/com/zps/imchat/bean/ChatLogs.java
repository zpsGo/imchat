package com.zps.imchat.bean;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * @author :zps
 * @desc: 聊天历史实体
 */
public class ChatLogs {
    private Long chatlogid;

    @SerializedName("from")
    private Long fromid;

    @SerializedName("to")
    private Long toid;

    private String content;

    private Date sendtime;

    @SerializedName("type")
    private String type;

    private Integer status;

    public Long getChatlogid() {
        return chatlogid;
    }

    public void setChatlogid(Long chatlogid) {
        this.chatlogid = chatlogid;
    }

    public Long getFromid() {
        return fromid;
    }

    public void setFromid(Long fromid) {
        this.fromid = fromid;
    }

    public Long getToid() {
        return toid;
    }

    public void setToid(Long toid) {
        this.toid = toid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSendtime() {
        return sendtime;
    }

    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


}

