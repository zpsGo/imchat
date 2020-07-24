package com.zps.imchat.jsonbean;

import com.zps.imchat.common.Status;
import lombok.Data;

@Data
public class ResponseJson <T>{

    //状态码
    int code;
    //错误信息
    String  errorMsg;
    //数据主体
    T data;

    public ResponseJson(T data){
        this.code=200;
        this.errorMsg="success";
        this.data=data;
    }

    public ResponseJson(Status s){
        this.code=s.getCode();
        this.errorMsg=s.getErrorMsg();
    }

    /**
     * 成功的时候调用
     */
    public static <T> ResponseJson<T> success(T data){
        return new ResponseJson<T>(data);
    }

    /**
     * 失败时调用
     */
    public static <T> ResponseJson<T> error(Status status){
        return new ResponseJson<T>(status);
    }

    public ResponseJson(Status  s,T  data){
        this.code=s.getCode();
        this.errorMsg=s.getErrorMsg();
        this.data=data;
    }
}
