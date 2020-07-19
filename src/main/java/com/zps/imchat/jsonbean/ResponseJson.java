package com.zps.imchat.jsonbean;

import com.zps.imchat.common.Status;

public class ResponseJson <T>{
    int code;
    String  errorMsg;
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

    public ResponseJson(Status  s,T  data){
        this.code=s.getCode();
        this.errorMsg=s.getErrorMsg();
        this.data=data;
    }
}
