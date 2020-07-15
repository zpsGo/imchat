package com.zps.imchat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: zps
 * @desc:主要是解决前后端请求的跨域问题
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //设置允许跨域的路径
        registry.addMapping("/**")
                //放行哪些原始域
                .allowedOrigins("*")
                //是否发送cookie信息
                .allowCredentials(true)
                //放行哪些请求方法
                .allowedMethods("*")
                //放行哪些头部信息
                .allowedHeaders("*")
                .maxAge(3600);
    }
}
