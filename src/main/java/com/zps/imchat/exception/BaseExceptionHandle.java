package com.zps.imchat.exception;


import com.zps.imchat.common.Status;
import com.zps.imchat.jsonbean.ResponseJson;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 统一异常处理
 * </p>
 *
 */
@ControllerAdvice
@ResponseBody
public class BaseExceptionHandle {

    @ExceptionHandler(Exception.class)  //拦截所有的异常
    public ResponseJson<String> exceptionHandle(Exception e){
        //在控制台打印错误信息
        e.printStackTrace();
        if(e instanceof BaseException){
            BaseException ex = (BaseException) e;
            return new ResponseJson(ex.getStatus());
        }else{
            return new ResponseJson(Status.UNKNOWN_ERROR);
        }
    }
}
