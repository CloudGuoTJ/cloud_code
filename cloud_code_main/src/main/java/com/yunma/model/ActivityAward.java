package com.yunma.model;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by ${CloudGoo} on 2017/5/3 0003.
 */
public class ActivityAward implements Serializable{
    private Integer activ_award_id;//奖品id
    private Integer activ_id;//活动id
    private Integer securityCodeId;//二维码id
    private String awardName;//奖品名字
    private int receiveAwardLevel;//兑奖等级
    private int isReceive;//是否已兑奖
    private Date receiveTime;//兑奖时间
    private Date createTime;//创建时间
    public Integer getActiv_award_id() {
        return activ_award_id;
    }

    public Integer getActiv_id() {
        return activ_id;
    }

    public Integer getSecurityCodeId() {
        return securityCodeId;
    }

    public String getAwardName() {
        return awardName;
    }

    public int getReceiveAwardLevel() {
        return receiveAwardLevel;
    }

    public int getIsReceive() {
        return isReceive;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setActiv_award_id(Integer activ_award_id) {
        this.activ_award_id = activ_award_id;
    }

    public void setActiv_id(Integer activ_id) {
        this.activ_id = activ_id;
    }

    public void setSecurityCodeId(Integer securityCodeId) {
        this.securityCodeId = securityCodeId;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

    public void setReceiveAwardLevel(int receiveAwardLevel) {
        this.receiveAwardLevel = receiveAwardLevel;
    }

    public void setIsReceive(int isReceive) {
        this.isReceive = isReceive;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


}
