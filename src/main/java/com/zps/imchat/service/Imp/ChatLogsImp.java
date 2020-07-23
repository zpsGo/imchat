package com.zps.imchat.service.Imp;

import com.zps.imchat.bean.ChatLogs;
import com.zps.imchat.mapper.ChatLogsDao;
import com.zps.imchat.service.ChatLogsService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author :zps
 * @desc: 聊天历史持服务层
 */
@Service
public class ChatLogsImp implements ChatLogsService {

    @Autowired
    ChatLogsDao chatLogsDao;

    @Override
    public List<com.zps.imchat.bean.ChatLogs> chatLogs(Long userId, int status) {
        return chatLogsDao.chatLogs(userId,status);
    }

    @Override
    public void saveLogs(ChatLogs chatLogs) {
        chatLogsDao.saveLogs(chatLogs);
    }

    @Override
    public List<ChatLogs> queryLogsOfSingle(Long fromid, Long toid, Long logIndex) {
        return chatLogsDao.queryLogsOfSingle(fromid , toid , logIndex);
    }

    @Override
    public List<ChatLogs> queryLogsOfGroup(Long fromid, Long toid, Long logIndex) {
        return chatLogsDao.queryLogsOfGroup(fromid , toid ,logIndex);
    }
}
