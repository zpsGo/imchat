package com.zps.imchat.jsonbean;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @author: zps
 * @desc : 消息实体类
 **/
public class MsgJson implements Serializable {

    //消息的类型
    private String type ;
    //消息主体
    private Map<String , String> dataMap ;
    //消息的扩展字段
    private String extand;

    private Date sendtime;

    public String getType() {
        return type;
    }

    public Date getSendtime() {
        return sendtime;
    }

    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, String> dataMap) {
        this.dataMap = dataMap;
    }

    public String getExtand() {
        return extand;
    }

    public void setExtand(String extand) {
        this.extand = extand;
    }
}
