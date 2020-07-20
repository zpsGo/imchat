package com.zps.imchat.controller;

import com.zps.imchat.bean.MyFriends;
import com.zps.imchat.service.MyFzService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author: zps
 * @desc : 好友控制层
 **/
@RestController
@RequestMapping("/friend")
@Api(tags = "好友相关接口")
public class MyFriendController {

    @Autowired
    MyFzService myFzService;

    @PostMapping("/addfriend")
    @ApiOperation(value="添加好友接口接口")
    public String addFriend(@RequestParam("myFzid") String myFzid , @RequestParam("userId") String userId ,
                            @RequestParam("nickname") String nickname){

        System.out.println(myFzid  + userId + nickname);
        MyFriends myFriends = new MyFriends();
        myFriends.setMyFzId(Long.parseLong(myFzid));
        myFriends.setUserId(Long.parseLong(userId));
        myFriends.setNickname(nickname);
        return myFzService.addFriend(myFriends).toString();

    }

    @PostMapping("/deletefriend")
    @ApiOperation(value = "移除好友")
    public String deleteFriend(@RequestParam("myFzid") String myFzid , @RequestParam("userId") String userId){

        myFzService.deleteFriend(Long.parseLong(myFzid) , Long.parseLong(userId));
        return "1";

    }





}
