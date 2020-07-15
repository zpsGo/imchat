package com.zps.imchat;

import com.zps.imchat.utils.SpringBeanUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.zps")   //扫描指定包下的组件
public class ImchatApplication {

    @Bean
    public SpringBeanUtil springBeanUtil(){
        return new SpringBeanUtil();
    }

    public static void main(String[] args) {
        SpringApplication.run(ImchatApplication.class, args);
    }

}
