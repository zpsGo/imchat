package com.zps.imchat;

import com.zps.imchat.bean.ChatLogs;
import com.zps.imchat.jsonbean.logscache.LogsCacheList;
import com.zps.imchat.utils.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: zps
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRedis {

    @Autowired
    RedisUtil redisUtil;

    @Test
    public void getString(){
        LogsCacheList user_1 = (LogsCacheList) redisUtil.hget("user_1", "1_2");
        System.out.println(user_1.getGmt_create());
        LogsCacheList logsCacheList = new LogsCacheList();
        logsCacheList.setChat_room_id("dnfsdj");
        logsCacheList.setGmt_create(new Date());
        logsCacheList.setLastest_new(new Date());
        redisUtil.hset("user_1", "1_3" , logsCacheList);
    }

    @Test
    public void getAll(){
        Map<Object, Object> user_1 = redisUtil.hmget("user_1");
        for(Map.Entry<Object, Object> entry : user_1.entrySet()){
            Date gmt_create = ((LogsCacheList) entry.getValue()).getGmt_create();
            System.out.println(gmt_create);
        }
    }

    @Test
    public void testList(){
        redisUtil.lSet("chat_23_21_20200724 10:00", 1);
        redisUtil.lSet("chat_23_21_20200724 10:00" , 2);
        List<Object> list = redisUtil.lGet("chat_23_21_20200724 10:00",0,-1);
        for(Object o : list){
             System.out.println(o);
        }
    }


    @Test
    public void testSort(){
        ChatLogs chatLogs = new ChatLogs();
        chatLogs.setStatus(1);
        redisUtil.add("chat_23_21_20200724_10:00" , chatLogs , new Date().getTime());
        chatLogs.setStatus(2);
        redisUtil.add("chat_23_21_20200724_10:00" , chatLogs , new Date().getTime());

        Set<Object> set = redisUtil.range("chat_23_21_20200724_10:00" , 0 , -1);
        for(Object o :set){
            System.out.println(o);
        }
    }

}
