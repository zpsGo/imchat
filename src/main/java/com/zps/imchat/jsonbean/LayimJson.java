package com.zps.imchat.jsonbean;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author :zps
 * @desc: 消息Json Bean
 */
@Data
public class LayimJson {
    private int code;
    private String msg;

    @SerializedName("data")
    private  MineJson mineResult;


}
