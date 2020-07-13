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
        return "hello world,我是冲突，增加了一点东西，万胜峰留的代码，啦啦啦啦啦啦啦" +
                "我在老一个冲图，怎么样呢？";
    }
}
