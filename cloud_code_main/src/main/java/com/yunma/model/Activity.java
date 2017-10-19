package com.yunma.model;

import java.io.Serializable;
import java.sql.Date;

/** 商家活动表
 * Created by ${CloudGoo} on 2017/5/3 0003.
 */
public class Activity implements Serializable{
    private static final long serialVersionUID = 1L;//阻止该变量被序列化到文件中,被反序列化破解
    private Integer activ_id;//活动id
    private Integer vendorId;//商家id
    private Integer orderId;//订单id
    private String activityName;//活动名称
    private int awardGet;//奖励的规格1.优惠券,2,红包,3,实物
    private  int awardGrant;//奖励发放方式1,系统生成.2,线下发放
    private int awardUse;//1,商场使用.2.商场使用
    private int level;//奖励级别
    private int levelOneNum;//1级奖励个数
    private int levelTwoNum;//2级奖励个数
    private int levelThreeNum;//````
    private int levelFourNum;
    private int levelFiveNum;
    private int levelSixNum;
    private int levelOneAwardId;//1级奖励id
    private int levelTwoAwardId;//```
    private int levelThreeAwardId;
    private int levelFourAwardId;
    private int levelFiveAwardId;
    private int levelSixAwardId;
    private String levelOneAwardName;//1级奖励名称
    private String levelTwoAwardName;//```
    private String levelThreeAwardName;
    private String levelFourAwardName;
    private String levelFiveAwardName;
    private String levelSixAwardName;
    private Date createTime;//创建时间
    private Date startTime;//执行起始时间
    private Date endTime;//过期时间
    private int status;//状态,是否发放完奖品0,未完 1,已完
    private String remark;//活动说明(备注)
    public Integer getActiv_id() {
        return activ_id;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public String getActivityName() {
        return activityName;
    }

    public int getAwardGet() {
        return awardGet;
    }

    public int getAwardGrant() {
        return awardGrant;
    }

    public int getAwardUse() {
        return awardUse;
    }

    public int getLevel() {
        return level;
    }

    public int getLevelOneNum() {
        return levelOneNum;
    }

    public int getLevelTwoNum() {
        return levelTwoNum;
    }

    public int getLevelThreeNum() {
        return levelThreeNum;
    }

    public int getLevelFourNum() {
        return levelFourNum;
    }

    public int getLevelFiveNum() {
        return levelFiveNum;
    }

    public int getLevelSixNum() {
        return levelSixNum;
    }

    public int getLevelOneAwardId() {
        return levelOneAwardId;
    }

    public int getLevelTwoAwardId() {
        return levelTwoAwardId;
    }

    public int getLevelThreeAwardId() {
        return levelThreeAwardId;
    }

    public int getLevelFourAwardId() {
        return levelFourAwardId;
    }

    public int getLevelFiveAwardId() {
        return levelFiveAwardId;
    }

    public int getLevelSixAwardId() {
        return levelSixAwardId;
    }

    public String getLevelOneAwardName() {
        return levelOneAwardName;
    }

    public String getLevelTwoAwardName() {
        return levelTwoAwardName;
    }

    public String getLevelThreeAwardName() {
        return levelThreeAwardName;
    }

    public String getLevelFourAwardName() {
        return levelFourAwardName;
    }

    public String getLevelFiveAwardName() {
        return levelFiveAwardName;
    }

    public String getLevelSixAwardName() {
        return levelSixAwardName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public int getStatus() {
        return status;
    }

    public String getRemark() {
        return remark;
    }

    public void setActiv_id(Integer activ_id) {
        this.activ_id = activ_id;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void setAwardGet(int awardGet) {
        this.awardGet = awardGet;
    }

    public void setAwardGrant(int awardGrant) {
        this.awardGrant = awardGrant;
    }

    public void setAwardUse(int awardUse) {
        this.awardUse = awardUse;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setLevelOneNum(int levelOneNum) {
        this.levelOneNum = levelOneNum;
    }

    public void setLevelTwoNum(int levelTwoNum) {
        this.levelTwoNum = levelTwoNum;
    }

    public void setLevelThreeNum(int levelThreeNum) {
        this.levelThreeNum = levelThreeNum;
    }

    public void setLevelFourNum(int levelFourNum) {
        this.levelFourNum = levelFourNum;
    }

    public void setLevelFiveNum(int levelFiveNum) {
        this.levelFiveNum = levelFiveNum;
    }

    public void setLevelSixNum(int levelSixNum) {
        this.levelSixNum = levelSixNum;
    }

    public void setLevelOneAwardId(int levelOneAwardId) {
        this.levelOneAwardId = levelOneAwardId;
    }

    public void setLevelTwoAwardId(int levelTwoAwardId) {
        this.levelTwoAwardId = levelTwoAwardId;
    }

    public void setLevelThreeAwardId(int levelThreeAwardId) {
        this.levelThreeAwardId = levelThreeAwardId;
    }

    public void setLevelFourAwardId(int levelFourAwardId) {
        this.levelFourAwardId = levelFourAwardId;
    }

    public void setLevelFiveAwardId(int levelFiveAwardId) {
        this.levelFiveAwardId = levelFiveAwardId;
    }

    public void setLevelSixAwardId(int levelSixAwardId) {
        this.levelSixAwardId = levelSixAwardId;
    }

    public void setLevelOneAwardName(String levelOneAwardName) {
        this.levelOneAwardName = levelOneAwardName;
    }

    public void setLevelTwoAwardName(String levelTwoAwardName) {
        this.levelTwoAwardName = levelTwoAwardName;
    }

    public void setLevelThreeAwardName(String levelThreeAwardName) {
        this.levelThreeAwardName = levelThreeAwardName;
    }

    public void setLevelFourAwardName(String levelFourAwardName) {
        this.levelFourAwardName = levelFourAwardName;
    }

    public void setLevelFiveAwardName(String levelFiveAwardName) {
        this.levelFiveAwardName = levelFiveAwardName;
    }

    public void setLevelSixAwardName(String levelSixAwardName) {
        this.levelSixAwardName = levelSixAwardName;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


}
