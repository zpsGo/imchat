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
    public static Status BIND_ERROR = new Status(501 ,"参数绑定有问题");
    /**
     * 拒绝访问
     */
    public static Status ACCESS_DENIED = new Status(403 ,"拒绝访问");
    /**
     * 邮箱已被注册
     */
    public static Status EMAIL_EXISTED = new Status(600 ,"该邮箱已注册");
    /**
     * 非法参数（格式校验）
     */
    public static Status ILLEGAL_PARAM = new Status(601 ,"非法参数");
    /**
     * 参数过期
     */
    public static Status PARAM_EXPIRED = new Status(602 ,"参数过期");

    /**
     * 校验失败
     */
    public static Status VALIDATION_FAIL = new Status(604 ,"验证失败");
    /**
     * 登录失败
     */
    public static Status LOGIN_FAILURE = new Status(603 ,"登陆失败");


    public static Status SIZE_EXCEED = new Status(605, "文件过大，无法上传");


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
