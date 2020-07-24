package com.zps.imchat.jsonbean;

import com.zps.imchat.bean.Group;
import com.zps.imchat.bean.MyFz;
import com.zps.imchat.bean.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :zps
 * @desc: 个人信息Json Bean
 */
@Data
public class MineJson {
   private User mine;
   private List<MyFz> friend=new ArrayList<>();
   private List<Group> group = new ArrayList<>();
}
