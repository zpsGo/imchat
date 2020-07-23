package com.zps.imchat.controller;

import com.zps.imchat.bean.Group;
import com.zps.imchat.common.Status;
import com.zps.imchat.jsonbean.ResponseJson;
import com.zps.imchat.service.GroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: zps
 * @desc : 群相关的控制层
 **/
@RestController
@RequestMapping("/group")
@Api(tags = "群相关接口")
public class GroupController {

    @Autowired
    GroupService groupService;

    @GetMapping("queryGroupById")
    @ApiOperation(value="根据群id获取群信息")
    public ResponseJson<Group> queryGroupById(@RequestParam("groupid") String groupid){
        Group group = groupService.finGroupByGroupId(Long.parseLong(groupid));
        if(group == null){
            return  ResponseJson.error(new Status(100 , "该群不存在"));
        }
        return ResponseJson.success(group);
    }


}
