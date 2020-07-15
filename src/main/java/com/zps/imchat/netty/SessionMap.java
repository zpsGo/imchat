package com.zps.imchat.netty;

import io.netty.channel.Channel;
import org.slf4j.LoggerFactory;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * @author: zps
 * @desc : 改类主要是用于保存userid 和 channnel 的对应关系的
 **/
public class SessionMap {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(SessionMap.class);

    public static ConcurrentHashMap<String , Channel> sessions = new ConcurrentHashMap<>();

    //把映射关系保存到map中
    public static void put(String userid , Channel channel){
        sessions.put(userid , channel);
    }

    //根据用户id获取channel
    public static Channel get(String userid){
        return sessions.get(userid);
    }


//    //移除过期的channel
//    public static void remove(Channel channel) {
//        sessions.remove(channel);
//    }
}
