package com.zps.imchat.controller;

import com.zps.imchat.bean.User;
import com.zps.imchat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: zps
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public String login(HttpServletRequest request){
        String id = request.getParameter("id");
        String pass = request.getParameter("pass");
        User user = userService.login(Long.parseLong(id), pass);
        if(StringUtils.isEmpty(user)){
            return "-1";
        }
        return "1";
    }

}
