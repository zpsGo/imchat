package com.zps.imchat.service;


import com.zps.imchat.bean.ChatLogs;

import java.util.List;


/**
 * @author :zps
 * @desc: 聊天历史持久层记录接口
 */
public interface ChatLogsService {

    public List<ChatLogs> chatLogs(Long userId, int status);

    void saveLogs(ChatLogs chatLogs);
}
