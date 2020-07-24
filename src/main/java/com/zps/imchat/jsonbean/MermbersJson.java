package com.zps.imchat.jsonbean;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author :zps
 * @desc: 成员信息Json Bean
 */
@Data
public class MermbersJson {
    private int code;

    private String msg;

    @SerializedName("data")
    private GroupJson data;

}