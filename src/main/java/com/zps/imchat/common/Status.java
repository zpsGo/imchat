package com.zps.imchat.common;

public enum Status {
    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),
    /**
     * 服务器异常
     */
    UNKNOWN_ERROR(500, "服务器出错"),
    /**
     * 参数无效
     */
    PRAM_INVALID(501, "参数无效");

    int code;
    String errorMsg;
    public static Status getStatus(int code){
        for(Status s:Status.values()){
            if(s.code==code)
                return s;
        }
        return null;
    }
    Status(int code, String errorMsg) {
        this.code = code;
        this.errorMsg = errorMsg;
    }

    public int getCode() {
        return code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
