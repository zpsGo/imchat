package com.zps.imchat.bean;

import java.io.Serializable;
/**
 * @author: zps
 * @desc : 用户实体类
 **/
public class User implements Serializable {

    private Long id;   //用户id

    private String username;   //用户昵称

    private  transient String pass;  //用户密码

    private String sign;     //用户签名

    private String status;   //用户状态

    private String avatar;   //用户图片

    private Integer sex;    //用户性别

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass == null ? null : pass.trim();
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign == null ? null : sign.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "ImUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", pass='" + pass + '\'' +
                ", sign='" + sign + '\'' +
                ", status='" + status + '\'' +
                ", avatar='" + avatar + '\'' +
                ", sex=" + sex +
                '}';
    }
}
