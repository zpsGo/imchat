package com.zps.imchat.utils.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @ClassName OSSUploadUtil
 * @Author DiangD
 * @Date 2020/7/28
 * @Version 1.0
 * @Description oss文件上传工具类
 **/
public class OSSUploadUtil {
    /**
     * @param ossClient  oss客户端
     * @param content    字符串内容
     * @param bucketName bucketName
     * @param objectName objectName
     */
    public static void uploadString(OSS ossClient, String content, String bucketName, String objectName) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, new ByteArrayInputStream(content.getBytes()));
        // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
        // ObjectMetadata metadata = new ObjectMetadata();
        // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
        // metadata.setObjectAcl(CannedAccessControlList.Private);
        // putObjectRequest.setMetadata(metadata);

        // 上传字符串。
        ossClient.putObject(putObjectRequest);

        // 关闭OSSClient。
        OSSClientUtil.shutDown(ossClient);

    }

    /**
     * @param ossClient  oss客户端
     * @param content    字符串内容
     * @param bucketName bucketName
     * @param objectName objectName
     */
    public static void uploadByteArray(OSS ossClient, String content, String bucketName, String objectName) {
        byte[] contentBytes = content.getBytes();
        // 上传字符串。
        ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(contentBytes));

        // 关闭OSSClient。
        OSSClientUtil.shutDown(ossClient);

    }

    /**
     * @param ossClient oss客户端
     * @param url 地址
     * @param bucketName bucketName
     * @param objectName objectName
     */
    public static void uploadByNetworkStream(OSS ossClient, String url, String bucketName, String objectName) {
        try {
            InputStream inputStream = new URL(url).openStream();
            ossClient.putObject(bucketName, objectName, inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            OSSClientUtil.shutDown(ossClient);
        }
    }

    /**
     * @param ossClient oss客户端
     * @param inputStream 文件流
     * @param bucketName bucketName
     * @param objectName objectName
     */
    public static void uploadByFileInputStream(OSS ossClient, InputStream inputStream, String bucketName, String objectName) {
        ossClient.putObject(bucketName, objectName, inputStream);
        OSSClientUtil.shutDown(ossClient);
    }

    /**
     * @param ossClient oss客户端
     * @param localFile 本地文件
     * @param bucketName bucketName
     * @param objectName objectName
     */
    public static void uploadLocalFile(OSS ossClient, File localFile, String bucketName, String objectName) {
        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, localFile);
        ossClient.putObject(putObjectRequest);
        OSSClientUtil.shutDown(ossClient);
    }


}

