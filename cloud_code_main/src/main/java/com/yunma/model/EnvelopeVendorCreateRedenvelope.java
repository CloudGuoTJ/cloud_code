package com.yunma.model;

import java.io.Serializable;
import java.sql.Date;

/**创建红包表
 * Created by ${CloudGoo} on 2017/5/4 0004.
 */
public class EnvelopeVendorCreateRedenvelope implements Serializable {
    private Integer red_env_id;//主键
    private Integer vendorId;//商家id
    private Integer rules_id;//规则id
    private Integer securityCodeId;//二维码id
    private Double envelopeMoney;//红包金额
    private int status;//领取状态
    private Integer order_id;//订单id
    private Date startTime;//红包发送时间
    private Date endTime;//红包结束时间
    private Integer sendEnvelopeId;//
    private String openId;//领取红包微信id
    public Integer getRed_env_id() {
        return red_env_id;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public Integer getRules_id() {
        return rules_id;
    }

    public Integer getSecurityCodeId() {
        return securityCodeId;
    }

    public Double getEnvelopeMoney() {
        return envelopeMoney;
    }

    public int getStatus() {
        return status;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Integer getSendEnvelopeId() {
        return sendEnvelopeId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setRed_env_id(Integer red_env_id) {
        this.red_env_id = red_env_id;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public void setRules_id(Integer rules_id) {
        this.rules_id = rules_id;
    }

    public void setSecurityCodeId(Integer securityCodeId) {
        this.securityCodeId = securityCodeId;
    }

    public void setEnvelopeMoney(Double envelopeMoney) {
        this.envelopeMoney = envelopeMoney;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setSendEnvelopeId(Integer sendEnvelopeId) {
        this.sendEnvelopeId = sendEnvelopeId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }


}
