package com.zps.imchat.jsonbean.logscache;

import com.zps.imchat.jsonbean.MsgJson;
import lombok.Data;

import java.util.Date;

/**
 * @author: zps
 * @desc : 缓存列表实体类
 **/
@Data
public class LogsCacheList {

    //聊天室号
    private String chat_room_id;
    //用户id
    private Long friendid;
    //创建时间
    private Date gmt_create;
    //修改时间
    private Date gtm_modify;
    //最后一条消息
    private String lastest_new;

}
