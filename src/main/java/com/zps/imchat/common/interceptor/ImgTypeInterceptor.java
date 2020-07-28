package com.zps.imchat.common.interceptor;

import com.alibaba.fastjson.JSON;
import com.zps.imchat.common.Status;
import com.zps.imchat.jsonbean.ResponseJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @ClassName ImgTypeInterceptor
 * @Author DiangD
 * @Date 2020/7/28
 * @Version 1.0
 * @Description 文件类型拦截器
 **/
@Slf4j
public class ImgTypeInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = true;
        // 判断是否为文件上传请求
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest multipartRequest =
                    (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> files =
                    multipartRequest.getFileMap();
            //对多部件请求资源进行遍历
            for (String formKey : files.keySet()) {
                MultipartFile multipartFile =
                        multipartRequest.getFile(formKey);
                assert multipartFile != null;
                String filename = multipartFile.getOriginalFilename();
                //判断是否为限制文件类型
                assert filename != null;
                if (!checkFile(filename)) {
                    ResponseJson<String> generate = new ResponseJson<>(Status.ILLEGAL_PARAM, "仅支持上传图片类型");
                    //限制文件类型，请求转发到原始请求页面，并携带错误提示信息
                    response.setContentType("application/json; charset=UTF-8");
                    response.getWriter().write(JSON.toJSONString(generate));
                    log.error("文件格式错误——{}", filename);
                    flag = false;
                }
            }
        }
        return flag;
    }

    //判断是否为图片类型
    private boolean checkFile(String fileName) {
        //设置允许上传文件类型
        String suffixList = "jpg,gif,png,ico,bmp,jpeg";
        // 获取文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        return suffixList.contains(suffix.trim().toLowerCase());
    }
}