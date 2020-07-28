package com.zps.imchat.service.Imp;

import com.aliyun.oss.OSS;
import com.zps.imchat.config.oss.AliOSSConfig;
import com.zps.imchat.service.FileUpLoadService;
import com.zps.imchat.utils.oss.FileUtil;
import com.zps.imchat.utils.oss.OSSClientFactory;
import com.zps.imchat.utils.oss.OSSUploadUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName FileUpLoadServiceImpl
 * @Author DiangD
 * @Date 2020/7/28
 * @Version 1.0
 * @Description 文件上传实现类
 **/
@Service
public class FileUpLoadServiceImpl implements FileUpLoadService {

    /**
     * 保证上传时线程安全
     */
    private static final Lock LOCK = new ReentrantLock();


    /**
     * @param file 文件
     * @return 文件在oss的路径
     * @Description: 上传单个文件
     */
    @Override
    public String uploadSingleFile(MultipartFile file) throws Exception {
        FileUtil fileUtil = new FileUtil();
        String res = null;
        if (file != null) {
            InputStream in = file.getInputStream();
            //生成文件名
            String path = fileUtil.createPath(file.getOriginalFilename());
            LOCK.lock();
            try {
                OSS oss = OSSClientFactory.getClient();
                //上传文件
                OSSUploadUtil.uploadByFileInputStream(oss, in, AliOSSConfig.bucketName, path);
                //获取在oss的链接
                res = AliOSSConfig.httpPath.concat(path);
            } finally {
                LOCK.unlock();
            }
        }
        return res;
    }
}
