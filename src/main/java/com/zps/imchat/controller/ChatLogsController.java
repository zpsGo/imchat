package com.zps.imchat.controller;

import com.zps.imchat.bean.ChatLogs;
import com.zps.imchat.common.Status;
import com.zps.imchat.jsonbean.MsgJson;
import com.zps.imchat.jsonbean.ResponseJson;
import com.zps.imchat.jsonbean.logscache.LogsCacheList;
import com.zps.imchat.service.ChatLogsService;
import com.zps.imchat.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author: zps
 **/
@RestController
@RequestMapping("chatLog")
@Api(tags = "聊天历史记录测试")
public class ChatLogsController {

    @Autowired
    ChatLogsService chatLogsService;

    @Autowired
    RedisUtil redisUtil;

    @GetMapping("queryLogsOfSingle")
    @ApiOperation(value = "获取与好友的聊天记录")
    public ResponseJson<List<ChatLogs>> queryLogsOfSingle(@RequestParam("fromid") String fromid ,
                                                    @RequestParam("toid") String toid,
                                                    @RequestParam("logIndex") String logIndex){

        List<ChatLogs> chatLogs = chatLogsService.queryLogsOfSingle(Long.parseLong(fromid),
                                         Long.parseLong(toid), Long.parseLong(logIndex));

        if(chatLogs.size() == 0){//没有数据
            return ResponseJson.error(new Status(100 , "没有更多好友的聊天记录了"));
        }
        return ResponseJson.success(chatLogs);
    }

    @GetMapping("queryLogsOfGroup")
    @ApiOperation(value = "获取群好友的聊天记录")
    public ResponseJson<List<ChatLogs>> queryLogsOfGroup(@RequestParam("fromid") String fromid ,
                                                          @RequestParam("toid") String toid,
                                                          @RequestParam("logIndex") String logIndex){

        List<ChatLogs> chatLogs = chatLogsService.queryLogsOfGroup(Long.parseLong(fromid),
                Long.parseLong(toid), Long.parseLong(logIndex));
        if(chatLogs.size() == 0){//没有数据
            return ResponseJson.error(new Status(100 , "没有更多的群聊天记录了"));
        }
        return ResponseJson.success(chatLogs);
    }

    @GetMapping("/getChatLogsList")
    @ApiOperation(value = "获取好友聊天历史列表")
    public ResponseJson<List<LogsCacheList>> getChatLogsList(@RequestParam("userid") String userid){

        Map<Object, Object> map = redisUtil.hmget("user_" + userid);
        List<LogsCacheList> lists = new ArrayList<>();
        for(Map.Entry<Object , Object> m : map.entrySet()){
            lists.add((LogsCacheList) m.getValue());
        }
        //根据时间进行排序
        lists.sort((a1 , a2) ->{
            try {
                a1.getGtm_modify().compareTo(a2.getGtm_modify());
            }catch (Exception e){
                e.printStackTrace();
            }
            return 1;
        });
        if(lists.size() == 0)
            return ResponseJson.error(new Status(603 , "你的聊天列表暂时为空，请跟好友进行聊天"));
        else
            return ResponseJson.success(lists);
    }

    @GetMapping("/getChatLogsHistory")
    @ApiOperation(value = "获取好友在redis中聊天历史")
    public ResponseJson<List<MsgJson>> getChatLogsHistory(@RequestParam("chat_room_id") String chat_room_id){
        //获取redis中的聊天历史
        List<Object> list = redisUtil.lGet(chat_room_id, 0, -1);
        List<MsgJson> msgJsonList = new ArrayList<>();
        for(Object o : list){
            msgJsonList.add((MsgJson) o);
        }
        if(msgJsonList.size() == 0){
            return ResponseJson.error(new Status(604 , "你们的聊天历史为空，请开始你们的聊天"));
        }else{
            return ResponseJson.success(msgJsonList);
        }
    }

}
