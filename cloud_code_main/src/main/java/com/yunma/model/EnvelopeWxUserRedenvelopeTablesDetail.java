package com.yunma.model;

import java.io.Serializable;
import java.sql.Date;

/** 用户中奖订单流水号表
 * Created by ${CloudGoo} on 2017/5/4 0004.
 */
public class EnvelopeWxUserRedenvelopeTablesDetail implements Serializable {
    private  Integer detail_id;
    private  String openId;
    private  String redEnvelopeTables;
    private  Date createTime;
    private  Date lastUpdateTime;
    public Integer getDetail_id() {
        return detail_id;
    }

    public String getOpenId() {
        return openId;
    }

    public String getRedEnvelopeTables() {
        return redEnvelopeTables;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setDetail_id(Integer detail_id) {
        this.detail_id = detail_id;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public void setRedEnvelopeTables(String redEnvelopeTables) {
        this.redEnvelopeTables = redEnvelopeTables;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }



}
