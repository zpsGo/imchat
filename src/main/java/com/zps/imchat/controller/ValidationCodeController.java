package com.zps.imchat.controller;

import com.zps.imchat.common.Status;
import com.zps.imchat.jsonbean.ResponseJson;
import com.zps.imchat.utils.EmailUtil;
import com.zps.imchat.utils.ValidationCodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

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
public class ValidationCodeController {
    @Value("${validation.code.length}")
    private Integer length;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/code")
    @ApiOperation(value = "获取邮箱验证码")
    @ApiImplicitParam(name = "email", value = "用户待注册的邮箱", required = true, dataType = "String",paramType = "query")
    public ResponseEntity<ResponseJson<String>> getValidationCode(@RequestParam("email") String email) {
        //匹配邮箱正则表达式
        if(!email.matches("^\\w+@(\\w+\\.)+\\w+$")){
            //不匹配直接返回错误信息
            return ResponseEntity.ok(new ResponseJson<>(Status.BIND_ERROR,"邮箱格式错误"));
        }
        //生成验证码
        String code = ValidationCodeUtil.createValidationCode(length);
        //生成key
        String key = ValidationCodeUtil.KEY_CODE_INDEX + email;
        ResponseJson<String> responseJson;
        if (redisTemplate.hasKey(key)) {
            //key存在，不允许重复获取验证码
            responseJson = new ResponseJson<>(Status.ACCESS_DENIED,"禁止频繁操作！");
            //http状态码设置为forbidden
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseJson);
        }
        //保存到redis
        redisTemplate.opsForValue().set(key, code, 30, TimeUnit.MINUTES);
        //发生邮件
        EmailUtil.sendEmail(email, ValidationCodeUtil.content(code, 30),"验证码" );
        //success
        responseJson = new ResponseJson<>("验证码已成功发到你的邮箱，请在30分钟内完成注册~");
        return ResponseEntity.ok(responseJson);
    }
}
