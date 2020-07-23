package com.zps.imchat.controller;

import com.zps.imchat.bean.User;
import com.zps.imchat.common.Status;
import com.zps.imchat.exception.BaseException;
import com.zps.imchat.jsonbean.LayimJson;
import com.zps.imchat.jsonbean.MermbersJson;
import com.zps.imchat.jsonbean.ResponseJson;
import com.zps.imchat.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: zps
 **/
@RestController
@Api(tags = "用户相关接口")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 测试异常捕获
     */
    @GetMapping("/error")
    @ApiOperation(value="测试全局统一异常处理")
    public ResponseJson<String> testerror(@RequestParam("id") Integer id){
        if(id == 0)
            return ResponseJson.error(new Status(-1 , "除数不能为0"));
        if(id == 1)
            throw new BaseException(new Status(0 , "测试抛出异常"));
        return ResponseJson.success("success");
    }

    //登录
    @PostMapping("/login")
    @ApiOperation(value="用户登录接口")
    public String login(@RequestParam("id") String id , @RequestParam("pass") String pass , HttpServletRequest request){
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
    @ApiOperation(value="获取好友信息接口")
    @ApiImplicitParam(name = "userid", value = "用户id", required = true, dataType = "String", paramType = "query")
    public ResponseJson<LayimJson> getMineInfo(@RequestParam("userid") String userid){    //获取登录初始化的信息

        return ResponseJson.success(userService.getMineInfo(Long.parseLong(userid)));

    }

    //获取群成员信息
    @GetMapping("/mermber")
    @ApiOperation(value="获取群成员信息接口")
    @ApiImplicitParam(name = "groupid", value = "群聊号", required = true, dataType = "String", paramType = "query")
    public ResponseJson<MermbersJson> getMermber(@RequestParam("groupid") String groupid){

        return ResponseJson.success(userService.getMermbers(Long.parseLong(groupid)));

    }


    //根据用户id查询用户信息
    @GetMapping("queryUserById")
    @ApiOperation(value="根据用户id获取用户信息")
    public ResponseJson<User> queryUserById(@RequestParam("id") String id){

        User user = userService.findUser(Long.parseLong(id));
        //用户不存在
        if(user == null){
            return ResponseJson.error(new Status(100 , "用户不存在"));
        }
        //返回用户信息
        return ResponseJson.success(user);

    }
}
