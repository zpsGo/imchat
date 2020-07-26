package com.zps.imchat.controller;

import com.google.gson.Gson;
import com.zps.imchat.bean.User;
import com.zps.imchat.common.Status;
import com.zps.imchat.exception.BaseException;
import com.zps.imchat.jsonbean.ResponseJson;
import com.zps.imchat.service.UserService;
import com.zps.imchat.utils.ValidationCodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author: zps
 **/
@RestController
@Api(tags = "用户相关接口")
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private Gson gson;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String DEFAULT_USER_AVATAR = "/images/boy-03.png";

    /**
     * 测试异常捕获
     */
    @GetMapping("/error")
    @ApiOperation(value = "测试全局统一异常处理")
    public ResponseJson<String> testerror(@RequestParam("id") Integer id) {
        if (id == 0)
            return ResponseJson.error(new Status(-1, "除数不能为0"));
        if (id == 1)
            throw new BaseException(new Status(0, "测试抛出异常"));
        return ResponseJson.success("success");
    }

    //登录
    @PostMapping("/login")
    @ApiOperation(value = "用户登录接口")
    public ResponseEntity<ResponseJson<Object>> login(@RequestParam("email") @Email() String email,
                                                      @RequestParam("pass") @Size(min = 6, max = 100, message = "密码格式错误") String pass,
                                                      HttpServletRequest request) {
        User user = User.builder()
                .email(email)
                .pass(pass)
                .build();
        //登录
        User loginUser = userService.login(user);
        if (loginUser != null) {
            request.getSession().setAttribute("userId", loginUser.getId());
            userService.updateUserStatus(loginUser.getId(), "online");
            return ResponseEntity.ok(new ResponseJson<>(Status.SUCCESS, loginUser));
        } else {                  //登录失败
            return ResponseEntity.ok(new ResponseJson<>(Status.LOGIN_FAILURE, "用户名或密码错误"));
        }
    }

    //获取好友信息
    @GetMapping("/mine")
    @ApiOperation(value = "获取好友信息接口")
    public ResponseJson<String> getMineInfo(@RequestParam("userid") String userid) {    //获取登录初始化的信息

        return ResponseJson.success(gson.toJson(userService.getMineInfo(Long.parseLong(userid))));

    }

    //获取群成员信息
    @GetMapping("/mermber")
    @ApiOperation(value = "获取群成员信息接口")
    @ApiImplicitParam(name = "groupid", value = "用户名", required = true, dataType = "String", paramType = "query")
    public ResponseJson<String> getMermber(@RequestParam("groupid") String groupid) {

        return ResponseJson.success(gson.toJson(userService.getMermbers(Long.parseLong(groupid))));

    }

    @PostMapping("/register")
    @ApiOperation(value = "用户注册接口")
    @ApiImplicitParam(name = "code", value = "验证码", required = true, dataType = "String", paramType = "query")
    public ResponseEntity<ResponseJson<String>> register(@Validated User user, @NotNull(message = "验证码不能为空") String code) {
        User userByEmail = userService.getUserByEmail(user.getEmail());
        //邮箱已被注册
        if (userByEmail != null) {
            return ResponseEntity.ok(new ResponseJson<>(Status.EMAIL_EXISTED, "邮箱已经注册过了~"));
        }
        //若用户没有上传头像，设置默认头像
        if (StringUtils.isEmpty(user.getAvatar())) {
            user.setAvatar(DEFAULT_USER_AVATAR);
        }
        //修改为字符串序列化
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        //从redis读取
        String validate = redisTemplate.opsForValue().get(ValidationCodeUtil.KEY_CODE_INDEX.concat(user.getEmail()));
        if (!StringUtils.isEmpty(validate) && code.equals(validate)) {
            //保存到数据库
            boolean isSuccess = userService.saveUser(user);
            if (isSuccess) {
                //注册成功，删除验证码缓存
                redisTemplate.delete(ValidationCodeUtil.KEY_CODE_INDEX.concat(user.getEmail()));
                return ResponseEntity.ok(ResponseJson.success("注册成功！"));
            }
        } else {
            //不管邮箱有没有拉取过验证码，一律为验证码失效
            return ResponseEntity.ok(new ResponseJson<>(Status.PARAM_EXPIRED, "验证码已过期！"));
        }
        return ResponseEntity.ok(new ResponseJson<>(Status.SERVER_ERROR, "注册失败！"));
    }
}
