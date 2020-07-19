package com.zps.imchat.exception;

import com.zps.imchat.common.Status;

public class BaseException extends RuntimeException {
    private Status status;
    public BaseException(Status status) {
        this.status=status;
    }
    public Status getStatus(){
        return status;
    }

}
