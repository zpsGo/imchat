package com.zps.imchat.utils.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.zps.imchat.config.oss.AliOSSConfig;

/**
 * @ClassName OSSClientFactory
 * @Author DiangD
 * @Date 2020/7/28
 * @Version 1.0
 * @Description 单例实现ossClient
 **/
public class OSSClientFactory {

    /**
     * @return OSS
     * @Description: 获取oss client
     */
    public static OSS getClient() {
        return new OSSClientBuilder()
                .build(AliOSSConfig.ossEndPoint, AliOSSConfig.accessKeyId, AliOSSConfig.accessKeySecret);
    }
}
