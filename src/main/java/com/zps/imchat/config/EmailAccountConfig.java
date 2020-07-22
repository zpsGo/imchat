package com.zps.imchat.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName EmailAccountConfig
 * @Author DiangD
 * @Date 2020/7/22
 * @Version 1.0
 * @Description 从配置文件读取邮件配置
 **/
@Configuration
@ConfigurationProperties(prefix = "email")
public class EmailAccountConfig {
    /**
     * 发送方
     */
    public static String sender;
    /**
     * 密码
     */
    public static String password;
    /**
     * 邮箱host
     */
    public static String host;


    public void setSender(String sender) {
        EmailAccountConfig.sender = sender;
    }

    public void setHost(String host) {
        EmailAccountConfig.host = host;
    }

    public void setPassword(String password) {
        EmailAccountConfig.password = password;
    }

}
