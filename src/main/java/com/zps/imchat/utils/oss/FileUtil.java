package com.zps.imchat.utils.oss;

import java.util.Calendar;
import java.util.UUID;

/**
 * @ClassName FileUtil
 * @Author DiangD
 * @Date 2020/7/28
 * @Version 1.0
 * @Description 文件工具类
 **/
public class FileUtil {
    /**
     * @Description: 生成文件名UUID
     */
    public String createFilename(String fileName) {
        fileName = fileName.trim();
        String extension = fileName.substring(fileName.lastIndexOf("."));
        return UUID.randomUUID() + extension;
    }

    /**
     * @Description: 生成文件路径
     */
    public String createPath(String fileName){
       fileName =  createFilename(fileName);
       return createRootDir() + "/" + fileName;
    }

    /**
     * @Description: 生成根路径（年月日）
     */
    public  String createRootDir() {
        StringBuilder stringBuilder = new StringBuilder();
        Calendar calendar = Calendar.getInstance();
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH));
        return stringBuilder.append(year).append(month).toString();
    }
}
