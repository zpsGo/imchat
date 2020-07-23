package com.zps.imchat.service;


import com.zps.imchat.bean.Group;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author :zps
 * @desc: 群持久层接口
 */
public interface GroupService {
    List<Group> findGroups(Long userId);

    Group finGroupByGroupId(Long groupid);
}
