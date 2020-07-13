package com.zps.imchat.bean;
/**
 * @author :zps
 * @desc:消息盒子实体
 */
public class MsgBox {
    private Long boxId;

    private Long uid;

    private Long fromid;

    private Long fromGroup;

    private Integer typ;

    private String remark;

    private String href;

    private Short read;

    private Long time;

    private User user;

    public Long getBoxId() {
        return boxId;
    }

    public void setBoxId(Long boxId) {
        this.boxId = boxId;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getFromid() {
        return fromid;
    }

    public void setFromid(Long fromid) {
        this.fromid = fromid;
    }

    public Long getFromGroup() {
        return fromGroup;
    }

    public void setFromGroup(Long fromGroup) {
        this.fromGroup = fromGroup;
    }

    public Integer getTyp() {
        return typ;
    }

    public void setTyp(Integer typ) {
        this.typ = typ;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Short getRead() {
        return read;
    }

    public void setRead(Short read) {
        this.read = read;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "MsgBox{" +
                "boxId=" + boxId +
                ", uid=" + uid +
                ", fromid=" + fromid +
                ", fromGroup=" + fromGroup +
                ", typ=" + typ +
                ", remark='" + remark + '\'' +
                ", href='" + href + '\'' +
                ", read=" + read +
                ", time=" + time +
                ", user=" + user +
                '}';
    }
}
