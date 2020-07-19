package com.zps.imchat;

import com.zps.imchat.rabbitmq.MqServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author: zps
 * @desc : 容器启动完后进行mq的初始化
 **/
@Configuration
public class MqStarter implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    MqServer mqServer;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        try {
            //初始化MQ
            mqServer.init();
            System.out.println("MQ服务初始化完成！");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
