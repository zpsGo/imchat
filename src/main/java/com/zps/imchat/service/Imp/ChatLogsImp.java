package com.zps.imchat.service.Imp;

import com.zps.imchat.mapper.ChatLogsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author :zps
 * @desc: 聊天历史持服务层
 */
@Service
public class ChatLogsImp implements ChatLogsDao {

    @Autowired
    ChatLogsDao chatLogs;

    @Override
    public List<com.zps.imchat.bean.ChatLogs> chatLogs(Long userId, int status) {
        return chatLogs.chatLogs(userId,status);
    }
}
