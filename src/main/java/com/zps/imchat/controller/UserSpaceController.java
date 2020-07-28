package com.zps.imchat.controller;

import com.zps.imchat.bean.User;
import com.zps.imchat.common.Status;
import com.zps.imchat.jsonbean.ResponseJson;
import com.zps.imchat.service.FileUpLoadService;
import com.zps.imchat.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Size;
import java.util.concurrent.*;

/**
 * @ClassName UserSpaceController
 * @Author DiangD
 * @Date 2020/7/26
 * @Version 1.0
 * @Description 用户个人空间控制器
 **/
@Validated
@RestController
@RequestMapping("/u")
@Slf4j
@Api(tags = "用户个人空间接口")
public class UserSpaceController {
    @Autowired
    private UserService userService;
    @Autowired
    private FileUpLoadService fileUpLoadService;

    private final ExecutorService threadPool = Executors.newCachedThreadPool();

    @PostMapping("/upload/avatar")
    @ApiOperation(value = "用户上传头像接口")
    public ResponseEntity<ResponseJson<String>> avatarUpload(@RequestParam("userId") Long userId,
                                                             @RequestParam("avatar") MultipartFile avatar,
                                                             HttpServletRequest req) {
        if (userId.equals(req.getSession().getAttribute("userId"))) {
                //定义上传任务
                Callable<String> callable = ()-> fileUpLoadService.uploadSingleFile(avatar);
                //获取future
                Future<String> future = threadPool.submit(callable);
            String path;
            try {
                //得到执行结果，否则阻塞
                path = future.get();
            } catch (InterruptedException | ExecutionException e) {
                log.error("图片上传失败——{}",avatar.getOriginalFilename());
                future.cancel(false);
                return ResponseEntity.ok(new ResponseJson<>(Status.SERVER_ERROR, "上传失败！"));
            }
            log.info("图片上传成功——{}",avatar.getOriginalFilename());
            return ResponseEntity.ok(new ResponseJson<>(Status.SUCCESS, path));
        } else {
            return new ResponseEntity<>(new ResponseJson<>(Status.ACCESS_DENIED, "没有该操作权限"), HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/{userId}/avatar")
    @ApiOperation(value = "用户更新头像接口")
    public ResponseEntity<ResponseJson<Object>> updateAvatar(@PathVariable("userId") Long userId,
                             @RequestParam("avatar") @Size(max = 100) String avatar,
                             HttpServletRequest req) {
        if (userId.equals(req.getSession().getAttribute("userId"))) {
            User user = User.builder()
                    .id(userId)
                    .avatar(avatar)
                    .build();
            //更新用户信息
            userService.updateUserInfo(user);
            //获取用户信息
            User userInfo = userService.getUserInfo(userId);
            return ResponseEntity.ok(new ResponseJson<>(Status.SUCCESS, userInfo));
        } else {
            return new ResponseEntity<>(new ResponseJson<>(Status.ACCESS_DENIED, "没有该操作权限"), HttpStatus.FORBIDDEN);
        }
    }


    @GetMapping("/{userId}")
    @ApiOperation(value = "用户获取个人信息接口")
    public ResponseEntity<ResponseJson<Object>> getUserInfo(@PathVariable Long userId, HttpServletRequest req) {
        if (userId.equals(req.getSession().getAttribute("userId"))) {
            //获取用户信息
            User userInfo = userService.getUserInfo(userId);
            return ResponseEntity.ok(new ResponseJson<>(Status.SUCCESS, userInfo));
        } else {
            return new ResponseEntity<>(new ResponseJson<>(Status.ACCESS_DENIED, "没有该操作权限"), HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/{userId}")
    @ApiOperation(value = "用户更新个人信息接口")
    public ResponseEntity<ResponseJson<Object>> setUserInfo(@Size(min = 2, max = 20, message = "用户名长度2~20") String username,
                                                            @Size(max = 100, message = "头像格式不正确") String avatar,
                                                            @Size(max = 60, message = "签名不能超过60字") String sign,
                                                            @Range(min = 0, max = 1) Integer sex,
                                                            @PathVariable Long userId,
                                                            HttpServletRequest req) {
        if (userId.equals(req.getSession().getAttribute("userId"))) {
            User user = User.builder()
                    .username(username)
                    .avatar(avatar)
                    .sign(sign)
                    .sex(sex)
                    .id(userId)
                    .status("online")
                    .build();
            //更新用户信息
            userService.updateUserInfo(user);
            //查询新的用户信息
            User userInfo = userService.getUserInfo(userId);
            return ResponseEntity.ok(new ResponseJson<>(Status.SUCCESS, userInfo));
        } else {
            return new ResponseEntity<>(new ResponseJson<>(Status.ACCESS_DENIED, "没有该操作权限"), HttpStatus.FORBIDDEN);
        }
    }

}
