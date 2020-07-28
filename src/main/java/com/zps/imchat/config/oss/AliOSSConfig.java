package com.zps.imchat.config.oss;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName AliOSSConfig
 * @Author DiangD
 * @Date 2020/7/28
 * @Version 1.0
 * @Description 阿里云oss配置类
 **/
@Getter
@Configuration
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliOSSConfig {
    public static String ossEndPoint;

    public static String accessKeyId;

    public static String accessKeySecret;

    public static String bucketName;

    public static String httpPath;


    public void setOssEndPoint(String ossEndPoint) {
        AliOSSConfig.ossEndPoint = ossEndPoint;
    }

    public void setAccessKeyId(String accessKeyId) {
        AliOSSConfig.accessKeyId = accessKeyId;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        AliOSSConfig.accessKeySecret = accessKeySecret;
    }

    public void setBucketName(String bucketName) {
        AliOSSConfig.bucketName = bucketName;
    }

    public void setHttpPath(String httpPath) {
        AliOSSConfig.httpPath = httpPath;
    }
}
