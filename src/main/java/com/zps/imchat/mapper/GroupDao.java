package com.zps.imchat.mapper;

import com.zps.imchat.bean.Group;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author :zps
 * @desc: 群持久层接口
 */
@Mapper
public interface GroupDao {

    List<Group> findGroups(Long userId);

    List<Group> findGroupsByUserId(Long userId);

    Group finGroupByGroupId(@Param("groupid") Long groupid);

}
