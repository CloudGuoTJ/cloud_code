package com.yunma.entity.redEnvelope;

import java.util.Date;

/**
 * 红包 抽奖记录表 
 */
public class RedEnv {

	private Integer id;			//
	private Integer orderId;	//订单id
	private Integer ruleId;		//规则id
	private Integer vendorId;	//厂商id
	private Integer securityCodeId;//二维码id
	private Double money;		//金额
	private Integer state;		//打开状态 0为打开 1 打开
	private Integer status;		//状态 0未领取 1领取
	private Date createTime;	//创建时间
	private Date startTime;		//生效时间
	private Date endTime;		//失效时间
	private String openId;		//openId
	private Date openTime;		//打开时间
	private Date receiveTime;	//领取时间
	
	private String startTimeStr;//生效时间
	private String endTimeStr;	//失效时间
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getRuleId() {
		return ruleId;
	}
	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public Integer getSecurityCodeId() {
		return securityCodeId;
	}
	public void setSecurityCodeId(Integer securityCodeId) {
		this.securityCodeId = securityCodeId;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getOpenTime() {
		return openTime;
	}
	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}
	public Date getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
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
