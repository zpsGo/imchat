package com.zps.imchat.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.zps.imchat.bean.User;
import com.zps.imchat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.sql.SQLSyntaxErrorException;

/**
 * @author: zps
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private Gson gson;

    //登录
    @PostMapping("/login")
    public String login(@RequestBody JSONObject jsonParam, HttpServletRequest request, Model model){
        System.out.println(jsonParam.get("id"));
        System.out.println(jsonParam.get("pass"));
        User user = new User();
        user.setId(Long.parseLong(jsonParam.getString("id")));
        user.setPass((String) jsonParam.get("pass"));
        User loginUser = userService.login(user);
        if(loginUser != null){
            //用户已经登录了
            if(loginUser.getStatus().equals("online"))
                return "0";
            //登录成功
            request.getSession().setAttribute("userId",loginUser.getId());
            userService.updateUserStatus(loginUser.getId(),"online");

            //保存会话Session
            request.getSession().setAttribute("userid" , user.getId());
            return "1";
        }else{                  //登录失败
            return "-1";
        }
    }

    //获取好友信息
    @GetMapping("/mine")
    public String getMineInfo(HttpServletRequest request){    //获取登录初始化的信息
//        return gson.toJson(userService.getMineInfo((long) 2));
        if(StringUtils.isEmpty(request.getSession().getAttribute("userId")))
            return "Session过期，请重新登录！";
        return gson.toJson(userService.getMineInfo((Long)request.getSession().getAttribute("userId")));
    }

    //获取群成员信息
    @GetMapping("/mermber")
    public String getMermber(@PathParam("groupid") String groupid){

        return gson.toJson(userService.getMermbers(Long.parseLong(groupid)));

    }
}
