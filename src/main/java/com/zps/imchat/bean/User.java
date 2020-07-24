package com.zps.imchat.bean;

import lombok.Data;

/**
 * @author :zps
 * @desc:用户实体类
 */
@Data
public class User {

    private Long id;

    private String username;

    private  transient String pass;

    private String sign;

    private String status;

    private String avatar;

    private Integer sex;

}