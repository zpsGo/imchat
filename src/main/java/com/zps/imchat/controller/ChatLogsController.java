package com.zps.imchat.controller;

import com.zps.imchat.bean.ChatLogs;
import com.zps.imchat.common.Status;
import com.zps.imchat.jsonbean.ResponseJson;
import com.zps.imchat.service.ChatLogsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: zps
 **/
@RestController
@RequestMapping("chatLog")
@Api(tags = "聊天历史记录测试")
public class ChatLogsController {

    @Autowired
    ChatLogsService chatLogsService;

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

}
