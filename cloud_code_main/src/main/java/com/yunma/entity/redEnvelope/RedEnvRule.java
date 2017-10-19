package com.yunma.entity.redEnvelope;

import java.util.Date;

/**
 * 红包规则
 */
public class RedEnvRule {
	
	private Integer id;			//
	private Integer vendorId;	//厂商id
	private String ruleName;	//规则名
	private Integer ruleLvel;	//红包等级/层数
	private Double oneRate;		//一等奖中奖概率
	private Double oneMinMoney;	//一等奖最小金额
	private Double oneMaxMoney;	//一等奖最大金额
	private Double twoRate;		//二等奖中奖概率
	private Double twoMinMoney;	//二等奖最小金额
	private Double twoMaxMoney;	//二等奖最大金额
	private Double threeRate;	//三等奖中奖概率
	private Double threeMinMoney;//三等奖最小金额
	private Double threeMaxMoney;//三等奖最大金额
	private Double fourRate;		//四等奖中奖概率
	private Double fourMinMoney;	//四等奖最小金额
	private Double fourMaxMoney;	//四等奖最大金额
	private Double fiveRate;		//五等奖中奖概率
	private Double fiveMinMoney;	//五等奖最小金额
	private Double fiveMaxMoney;	//五等奖最大金额
	private Date createTime;	//创建时间
	private String rule_type;  //规则类型
	private String send_name;  //发放者名称
	private String wishing; //祝福语
	
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
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public Integer getRuleLvel() {
		return ruleLvel;
	}
	public void setRuleLvel(Integer ruleLvel) {
		this.ruleLvel = ruleLvel;
	}
	public Double getOneRate() {
		return oneRate;
	}
	public void setOneRate(Double oneRate) {
		this.oneRate = oneRate;
	}
	public Double getOneMinMoney() {
		return oneMinMoney;
	}
	public void setOneMinMoney(Double oneMinMoney) {
		this.oneMinMoney = oneMinMoney;
	}
	public Double getOneMaxMoney() {
		return oneMaxMoney;
	}
	public void setOneMaxMoney(Double oneMaxMoney) {
		this.oneMaxMoney = oneMaxMoney;
	}
	public Double getTwoRate() {
		return twoRate;
	}
	public void setTwoRate(Double twoRate) {
		this.twoRate = twoRate;
	}
	public Double getTwoMinMoney() {
		return twoMinMoney;
	}
	public void setTwoMinMoney(Double twoMinMoney) {
		this.twoMinMoney = twoMinMoney;
	}
	public Double getTwoMaxMoney() {
		return twoMaxMoney;
	}
	public void setTwoMaxMoney(Double twoMaxMoney) {
		this.twoMaxMoney = twoMaxMoney;
	}
	public Double getThreeRate() {
		return threeRate;
	}
	public void setThreeRate(Double threeRate) {
		this.threeRate = threeRate;
	}
	public Double getThreeMinMoney() {
		return threeMinMoney;
	}
	public void setThreeMinMoney(Double threeMinMoney) {
		this.threeMinMoney = threeMinMoney;
	}
	public Double getThreeMaxMoney() {
		return threeMaxMoney;
	}
	public void setThreeMaxMoney(Double threeMaxMoney) {
		this.threeMaxMoney = threeMaxMoney;
	}
	public Double getFourRate() {
		return fourRate;
	}
	public void setFourRate(Double fourRate) {
		this.fourRate = fourRate;
	}
	public Double getFourMinMoney() {
		return fourMinMoney;
	}
	public void setFourMinMoney(Double fourMinMoney) {
		this.fourMinMoney = fourMinMoney;
	}
	public Double getFourMaxMoney() {
		return fourMaxMoney;
	}
	public void setFourMaxMoney(Double fourMaxMoney) {
		this.fourMaxMoney = fourMaxMoney;
	}
	public Double getFiveRate() {
		return fiveRate;
	}
	public void setFiveRate(Double fiveRate) {
		this.fiveRate = fiveRate;
	}
	public Double getFiveMinMoney() {
		return fiveMinMoney;
	}
	public void setFiveMinMoney(Double fiveMinMoney) {
		this.fiveMinMoney = fiveMinMoney;
	}
	public Double getFiveMaxMoney() {
		return fiveMaxMoney;
	}
	public void setFiveMaxMoney(Double fiveMaxMoney) {
		this.fiveMaxMoney = fiveMaxMoney;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getRule_type() {
		return rule_type;
	}
	public void setRule_type(String rule_type) {
		this.rule_type = rule_type;
	}
	public String getSend_name() {
		return send_name;
	}
	public void setSend_name(String send_name) {
		this.send_name = send_name;
	}
	public String getWishing() {
		return wishing;
	}
	public void setWishing(String wishing) {
		this.wishing = wishing;
	}
	
	
}
