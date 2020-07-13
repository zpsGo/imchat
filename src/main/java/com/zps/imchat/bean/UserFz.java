package com.zps.imchat.bean;

/**
 * @author :zps
 * @desc:用户分组实体
 */
public class UserFz {
    private Integer id;

    private Long userId;

    private Long fzId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFzId() {
        return fzId;
    }

    public void setFzId(Long fzId) {
        this.fzId = fzId;
    }
}