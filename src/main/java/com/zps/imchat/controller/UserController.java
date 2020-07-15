package com.zps.imchat.controller;

import com.google.gson.Gson;
import com.zps.imchat.bean.User;
import com.zps.imchat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public String login(@PathParam("id") String id , @PathParam("pass") String pass , HttpServletRequest request){
        //判断输入是否合法
        if(StringUtils.isEmpty(id) || StringUtils.isEmpty(pass))
            return "-1";
        Pattern pattern = Pattern.compile("^[0-9]*$");
        Matcher matcher = pattern.matcher(id);
        if(!matcher.matches()) return "-1";
        //验证数据库
        User user = new User();
        user.setId(Long.parseLong(id));
        user.setPass(pass);
        User loginUser = userService.login(user);
        if(loginUser != null){
            //用户已经登录了
//            if(loginUser.getStatus().equals("online"))
//                return "0";
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
