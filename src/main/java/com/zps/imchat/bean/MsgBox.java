package com.zps.imchat.bean;

import lombok.Data;

/**
 * @author :zps
 * @desc:消息盒子实体
 */
@Data
public class MsgBox {
    private Long boxId;

    private Long uid;

    private Long fromid;

    private Long fromGroup;

    private Integer typ;

    private String remark;

    private String href;

    private Short read;

    private Long time;

    private User user;
}
