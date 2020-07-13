package com.zps.imchat.mapper;


import com.zps.imchat.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author　: zps
 * @desc ：用户持久层
 **/
@Mapper
public interface UserMapper {

     User login(@Param("id") Long id , @Param("pass") String pass);


}
