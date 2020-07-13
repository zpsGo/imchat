package com.zps.imchat.service.Imp;

import com.zps.imchat.mapper.UserFzDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author :zps
 * @desc: 用户分组服务层
 */
@Service
public class UserFzImp implements UserFzDao {

    @Autowired
    UserFzDao userFzDao;
    @Override
    public List<Long> findFzId(long userId) {
        return userFzDao.findFzId(userId);
    }
}
