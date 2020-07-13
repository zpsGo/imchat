package com.zps.imchat.service.Imp;


import com.zps.imchat.bean.Group;
import com.zps.imchat.mapper.GroupDao;
import com.zps.imchat.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author :zps
 * @desc: 群服务层
 */
@Service
public class GroupImp implements GroupService {

    @Autowired
    GroupDao groupDao;

    @Override
    public List<Group> findGroups(Long userId) {
        return groupDao.findGroups(userId);
    }
}
