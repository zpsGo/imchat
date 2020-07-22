package com.zps.imchat.common;

public class Status {
//    /**
//     * 操作成功
//     */
//    SUCCESS(200, "操作成功"),
//    /**
//     * 服务器异常
//     */
//    UNKNOWN_ERROR(500, "服务器出错"),
//    /**
//     * 参数无效
//     */
//    PRAM_INVALID(501, "参数无效");

    //定义一些常用的错误信息
    public static Status SUCCESS = new Status(200 , "success");
    public static Status SERVER_ERROR = new Status(500,"服务端异常！");
    public static Status BIND_ERROR = new Status(50100 ,"参数绑定有问题");


    int code;

    String errorMsg;

    public Status(int code, String errorMsg) {
        this.code = code;
        this.errorMsg = errorMsg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getCode() {
        return code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * 返回带参数的错误码
     * @param args
     * @return
     */
    public Status fillArgs(Object... args) {
        int code = this.code;
        String message = String.format(this.errorMsg, args);
        return new Status(code, message);
    }

    @Override
    public String toString() {
        return "Status{" +
                "code=" + code +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
