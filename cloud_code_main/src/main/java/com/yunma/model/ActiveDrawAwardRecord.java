package com.yunma.model;

import java.io.Serializable;
import java.sql.Date;

/**商家活动中奖记录表
 * Created by ${CloudGoo} on 2017/5/3 0003.
 */
public class ActiveDrawAwardRecord implements Serializable {
    private  int activ_record_id;
    private Integer vendorId;
    private Integer activityId;
    private String activityName;
    private int awardId;
    private String awaedName;
    private String securityCode;
    private String oppenId;
    private String  recipientName;
    private String recipientPhone;
    private Date receiveTime;
    private Date createTime;
    public int getActiv_record_id() {
        return activ_record_id;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public int getAwardId() {
        return awardId;
    }

    public String getAwaedName() {
        return awaedName;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public String getOppenId() {
        return oppenId;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setActiv_record_id(int activ_record_id) {
        this.activ_record_id = activ_record_id;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void setAwardId(int awardId) {
        this.awardId = awardId;
    }

    public void setAwaedName(String awaedName) {
        this.awaedName = awaedName;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public void setOppenId(String oppenId) {
        this.oppenId = oppenId;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }



}
