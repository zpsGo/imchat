package com.zps.imchat.controller;

import com.zps.imchat.bean.User;
import com.zps.imchat.common.Status;
import com.zps.imchat.jsonbean.ResponseJson;
import com.zps.imchat.service.UserService;
import com.zps.imchat.utils.EmailUtil;
import com.zps.imchat.utils.ValidationCodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Email;
import java.util.concurrent.*;

/**
 * @ClassName ValidationCodeController
 * @Author DiangD
 * @Date 2020/7/22
 * @Version 1.0
 * @Description 生成验证码的控制器
 **/
@RestController
@RequestMapping(value = "/verify")
@Api(tags = "获取验证码接口")
@Validated
public class ValidationCodeController {
    @Value("${validation.code.length}")
    private Integer length;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private UserService userService;

    /**
     * 可能发生oom
     */
    private final ExecutorService executorService = Executors.newCachedThreadPool();


    @PostMapping("/code")
    @ApiOperation(value = "获取邮箱验证码")
    @ApiImplicitParam(name = "email", value = "用户待注册的邮箱", required = true, dataType = "String", paramType = "query")
    public ResponseEntity<ResponseJson<String>> getValidationCode( @Email(message = "邮箱格式错误") @RequestParam("email") String email) {
        User user = userService.getUserByEmail(email);
        if (user!=null) {
            return ResponseEntity.ok(new ResponseJson<>(Status.EMAIL_EXISTED,"邮箱已经注册过了~"));
        }
        //生成验证码
        String code = ValidationCodeUtil.createValidationCode(length);
        //生成key
        String key = ValidationCodeUtil.KEY_CODE_INDEX + email;
        ResponseJson<String> responseJson;
        //一分钟内一个账号不能多次请求此接口
        if (redisTemplate.getExpire(key, TimeUnit.MINUTES) >= ValidationCodeUtil.DEFAULT_CODE_EXPIRE_MIN - 1) {
            //key存在，不允许重复获取验证码
            responseJson = new ResponseJson<>(Status.ACCESS_DENIED, "禁止频繁操作！");
            //http状态码设置为forbidden
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseJson);
        }
        //配置文件事以json存储，不方便直接操作字符串，修改为字符串系列化
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        //保存到redis，会覆盖原来的值
        redisTemplate.opsForValue().set(key, code, ValidationCodeUtil.DEFAULT_CODE_EXPIRE_MIN, TimeUnit.MINUTES);
//        线程已经关闭就重新生成
//        if (executorService.isTerminated()) {
//            executorService = Executors.newFixedThreadPool(20);
//        }
        //发送邮件，使用线程池提高性能
        Future<Integer> future = executorService
                .submit(() -> emailUtil.sendEmail(email, ValidationCodeUtil.content(code, ValidationCodeUtil.DEFAULT_CODE_EXPIRE_MIN), "验证码"));
        //success
        try {
            //邮件成功发生的情况
            if (future.get() == 1) {
                responseJson = new ResponseJson<>("验证码已成功发到你的邮箱，请在30分钟内完成注册~");
                return ResponseEntity.ok(responseJson);
            }
        } catch (ExecutionException | InterruptedException e) {
            //发生异常取消任务
            future.cancel(false);
        }
        //发送失败
        return ResponseEntity.ok(new ResponseJson<>(Status.SERVER_ERROR, "验证码发送失败，请重试！"));
    }
}
