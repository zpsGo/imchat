package com.zps.imchat.bean;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

/**
 * @author :zps
 * @desc:用户实体类
 */
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {

    private Long id;
    @NotEmpty(message = "用户名不能为空")
    @Size(min = 2, max = 20,message = "用户名长度必须在2到20之间")
    private String username;

    @NotEmpty(message = "密码不能为空")
    @Size(min =6,max = 100,message = "密码过长")
    private transient String pass;

    @Size(max = 60,message = "个性签名长度不得超过60")
    private String sign;

    private String status;

    @Size(max = 100)
    private String avatar;
    @Range(min = 0,max = 1)
    private Integer sex;
    @Email(message = "邮箱格式错误")
    @NotEmpty(message = "邮箱不能为空")
    @Size(max = 40)
    private String email;


}