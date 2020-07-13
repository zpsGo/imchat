package com.zps.imchat.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author :zps
 * @desc: 聊天历史持久层记录接口
 */
@Mapper
public interface ChatLogsDao {
    public List<com.zps.imchat.bean.ChatLogs> chatLogs(@Param("userId") Long userId, @Param("status") int status);
}
