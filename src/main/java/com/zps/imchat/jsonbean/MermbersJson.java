package com.zps.imchat.jsonbean;

import com.google.gson.annotations.SerializedName;

/**
 * @author :zps
 * @desc: 成员信息Json Bean
 */
public class MermbersJson {
    private int code;

    private String msg;

    @SerializedName("data")
    private GroupJson data;

    @Override
    public String toString() {
        return "MermbersJson{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
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

    public GroupJson getData() {
        return data;
    }

    public void setData(GroupJson data) {
        this.data = data;
    }
}
