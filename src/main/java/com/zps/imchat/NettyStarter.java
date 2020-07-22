package com.zps.imchat;

import com.zps.imchat.netty.NettyServer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author: zps
 * @desc ：Netty服务启动器，当监听到容器刷新后，就启动服务
 **/

@Component
public class NettyStarter implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        try {
            //启动服务
            NettyServer.getInstance().start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
