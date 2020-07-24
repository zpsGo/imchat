package com.zps.imchat.bean;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author :zps
 * @desc：群信息
 */
@Data
public class Group {
    @SerializedName("id")
    private Long group_id;
    private String groupname;
    private String avator;
    private String notice;
    private Long user_id;
    private Integer approve;
}
