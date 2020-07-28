package com.zps.imchat.utils.oss;

import com.aliyun.oss.OSS;

/**
 * @ClassName OSSClientUtil
 * @Author DiangD
 * @Date 2020/6/7
 * @Version 1.0
 * @Description OSSClient工具类
 **/
public class OSSClientUtil {
    /**
     * @param ossClient oss客户端
     */
    public static void shutDown(OSS ossClient) {
        if (ossClient!=null) {
            ossClient.shutdown();
        }
    }

    /**
     * @param ossClient oss客户端
     * @param bucketName bucketName
     * @param key path
     */
    public static void removeFile(OSS ossClient, String bucketName, String key) {
        ossClient.deleteObject(bucketName, key);
    }
}
