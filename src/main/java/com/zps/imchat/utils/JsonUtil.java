package com.zps.imchat.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zps.imchat.bean.UserFz;
import com.zps.imchat.jsonbean.MsgJson;

import java.util.*;

/**
 * @author: zps
 * @desc：格式转换工具
 **/
public class JsonUtil {

    //谷歌 GsonBuilder 构造器
    static GsonBuilder gb = new GsonBuilder();

    private static final Gson gson;

    static {
        //不需要html escape
        gb.disableHtmlEscaping();
        gson = gb.create();
    }

    //将实体类转换为json
    public static String pojoTojson(Object obj){
        return gson.toJson(obj);
    }

    //将json装换为对象
    public static <T> T jsonTopojo(String json , Class<T> tClass){
        T t = gson.fromJson(json, tClass);
        return t;
    }

    public static void main(String args[]){
        List<MsgJson> msgList = new ArrayList<>();
        for(int i = 0; i < 4; i ++){
            MsgJson msgJson = new MsgJson();
            msgJson.setType("chat");
            msgJson.setExtand("额外的消息");
            msgJson.setSendtime(new Date());
            Map<String ,String> map = new HashMap<>();
            map.put("from" , "1");
            map.put("to" , "2");
            map.put("msg" , "你好");
            msgJson.setDataMap(map);
            msgList.add(msgJson);
        }
        String str = pojoTojson(msgList);
        System.out.println(str);
//        str = "{\"type\":\"connect\",\"dataMap\":{\"userid\":\"1\"}}";

//        MsgJson msgJson1 = jsonTopojo(str , MsgJson.class);
//        System.out.println(msgJson1.getDataMap().get("from"));


//        UserFz userFz = new UserFz();
//        userFz.setId(1);
//        userFz.setFzId((long) 32);
//        userFz.setUserId((long) 1);
//        String str = pojoTojson(userFz);
//        System.out.println(str);
//
//        UserFz userFz1 = jsonTopojo(str, UserFz.class);
//        System.out.println(userFz.getId());
    }
}
