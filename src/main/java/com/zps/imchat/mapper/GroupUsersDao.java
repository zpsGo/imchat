package com.zps.imchat.mapper;

import com.zps.imchat.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * @author :zps
 * @desc: 群员信息持久层接口
 */
@Mapper
public interface GroupUsersDao {
    List<User> findGropUsers(Long groupid);

    List<Long> findGroupUsersId(@Param("groupId") Long groupId);

    User getUserByGroupId(@Param("groupId") Long groupId);

    Long findUserIdByGroupId(@Param("groupId") Long groupId);

}
