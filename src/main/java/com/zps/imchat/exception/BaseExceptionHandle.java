package com.zps.imchat.exception;


import com.zps.imchat.common.Status;
import com.zps.imchat.jsonbean.ResponseJson;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


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
            //捕获相应的异常
            BaseException ex = (BaseException) e;
            return ResponseJson.error(ex.getStatus());
        }else if(e instanceof BindException){
            BindException eb = (BindException)e;
            //绑定错误返回很多错误，是一个错误列表，只需要第一个错误
            List<ObjectError> list = eb.getAllErrors();
            ObjectError error = list.get(0);
            String msg = error.getDefaultMessage();
            //给状态码填充参数
            return ResponseJson.error(Status.BIND_ERROR.fillArgs(msg));
        } else{
            //否则，统一报服务端异常
            return ResponseJson.error(Status.SERVER_ERROR);
        }
    }
}
