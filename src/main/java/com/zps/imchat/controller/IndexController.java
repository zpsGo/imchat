package com.zps.imchat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zps
 **/
@RestController
public class IndexController {

    @GetMapping("/index")
    public String index(){
        return "hello world,强行冲突，万胜峰留的代码";
    }
}
