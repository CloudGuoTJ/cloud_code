package com.yunma.model;

import java.io.Serializable;
import java.sql.Date;

/**中奖统计表
 * Created by ${CloudGoo} on 2017/5/4 0004.
 */
public class EnvelopeDrawRedenvelopeRecord implements Serializable {
    private Integer activ_record_id;
    private Integer vendorId;
    private Integer activityId;
    private String activityName;
    private String awardName;
    private String securityCode;
    private  String openId;
    private String recipientName;
    private String recipientPhone;
    private Date receiveTime;
    private Date createTime;
    public Integer getActiv_record_id() {
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

    public String getAwardName() {
        return awardName;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public String getOpenId() {
        return openId;
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

    public void setActiv_record_id(Integer activ_record_id) {
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

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
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
