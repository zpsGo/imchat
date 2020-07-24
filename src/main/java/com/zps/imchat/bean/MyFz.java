package com.zps.imchat.bean;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author :zps
 * @desc:分组朋友信息实体
 */
@Data
public class MyFz implements Serializable {

    @SerializedName("id")
    private Long fzId;

    @SerializedName("groupname")
    private String fzGroupname;

    @SerializedName("list")
    private List<User> users=new ArrayList<>();

}
