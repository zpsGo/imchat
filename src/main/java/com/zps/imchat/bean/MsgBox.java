package com.zps.imchat.bean;

import lombok.Data;

import java.util.Date;

/**
 * @author :zps
 * @desc:消息盒子实体
 */
@Data
public class MsgBox {

    private Long boxId;

    private String type;   //消息盒类型

    private Long noticer_id; //被通知人

    private Long userid;  //发起通知的人，如好友申请人，群申请人

    private Integer fzid;  //好友申请专属

    private String avatar; //申请人的图片或者群图片

    private String extand;  //拓展字段，如申请信息

    private Date sendtime;  //申请时间

}
