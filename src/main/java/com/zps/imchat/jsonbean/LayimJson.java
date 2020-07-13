package com.zps.imchat.jsonbean;

import com.google.gson.annotations.SerializedName;

/**
 * @author :zps
 * @desc: 消息Json Bean
 */
public class LayimJson {
    private int code;
    private String msg;

    @SerializedName("data")
    private  MineJson mineResult;

    public MineJson getMineResult() {
        return mineResult;
    }

    public void setMineResult(MineJson mineResult) {
        this.mineResult = mineResult;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
