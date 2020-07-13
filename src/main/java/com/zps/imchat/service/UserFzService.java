package com.zps.imchat.service;

import java.util.List;

/**
 * @author :zps
 * @desc: 用户分组持久层接口
 */
public interface UserFzService {
    //查找分组id
    List<Long> findFzId(long userId);
}
