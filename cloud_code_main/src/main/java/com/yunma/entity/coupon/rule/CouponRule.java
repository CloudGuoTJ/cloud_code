package com.yunma.entity.coupon.rule;

import java.util.Date;

/**
 * 优惠券规则
 */
public class CouponRule {

	private Integer id;		//id
	private Integer vendorId;//厂商id
	private Integer tagId;	//规则标签id
	private String ruleName;//规则名
	private Date createTime;//创建时间
	private Date startTime;	//开始时间
	private Date endTime;	//结束时间
	private String state;	//状态
	private String timeType;//发放周期类型
	private String isBlackWhite;//是否设置了黑白名单  -1 不设置 0 黑白名单 1黑名单 2白名单
	private String isScope;//发放载体   3 全部   4  产品  5订单
	private String isDefault;//0 不是默认规则  1是默认规则
	
	private String startTimeStr;
	private String endTimeStr;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public Integer getTagId() {
		return tagId;
	}
	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getTimeType() {
		return timeType;
	}
	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}
	public String getIsBlackWhite() {
		return isBlackWhite;
	}
	public void setIsBlackWhite(String isBlackWhite) {
		this.isBlackWhite = isBlackWhite;
	}
	public String getIsScope() {
		return isScope;
	}
	public void setIsScope(String isScope) {
		this.isScope = isScope;
	}
	public String getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	public String getStartTimeStr() {
		return startTimeStr;
	}
	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}
	public String getEndTimeStr() {
		return endTimeStr;
	}
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	
}
