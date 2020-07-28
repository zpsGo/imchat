package com.zps.imchat.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author DiangD
 * @version 1
 * @ClassName FileUpLoadService
 * @Description: 文件上传接口
 */
public interface FileUpLoadService {
    String uploadSingleFile(MultipartFile file) throws IOException, Exception;
}
