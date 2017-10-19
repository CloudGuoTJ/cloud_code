package com.yunma.entity.redEnvelope;

import java.io.Serializable;
import java.util.Date;

/**
 * 红包总表 （统计表）
 */
public class RedEnvelope implements Serializable{

	private Integer id;				//
	private Integer vendorId;		//订单id
	private Integer orderId;		//订单id
	private Integer ruleId;			//规则id
	private String ruleName;		//规则名称
	private Double rechargeMoney;	//充值金额
	private Double consumeMoney;	//消费金额
	private Date createTime;		//创建时间
	private Date startTime;			//生效时间
	private Date endTime;			//失效时间
	private Integer status;			//状态 0 失效 1生效
	
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
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public Double getRechargeMoney() {
		return rechargeMoney;
	}
	public void setRechargeMoney(Double rechargeMoney) {
		this.rechargeMoney = rechargeMoney;
	}
	public Double getConsumeMoney() {
		return consumeMoney;
	}
	public void setConsumeMoney(Double consumeMoney) {
		this.consumeMoney = consumeMoney;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
