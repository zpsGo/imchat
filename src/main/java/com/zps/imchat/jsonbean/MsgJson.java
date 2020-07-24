package com.zps.imchat.jsonbean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @author: zps
 * @desc : 消息实体类
 **/
@Data
public class MsgJson implements Serializable {

    //消息的类型
    private String type ;
    //消息主体
    private Map<String , String> dataMap ;
    //消息的扩展字段
    private String extand;

    private Date sendtime;


}
