package com.zps.imchat.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author :zps
 * @desc: 用户分组持久层接口
 */
@Mapper
public interface UserFzDao {

    //查找分组id
    List<Long> findFzId(long userId);

}
