package com.yunma.entity.vendorIntegral;

import java.io.Serializable;
import java.sql.Date;


/**
 * 积分系统规则表
 * @author Administrator
 *
 */
public class VendorIntegralRule implements Serializable{

	
	private Integer inteRuleId; // '规则Id

	private String ruleName; // 规则名称

	private Integer productId; // 产品id

	private String productName; // 产品名字

	private Integer vendorId; // 厂商id

	private String vendorName; // 厂商名字
	
	private Integer isUseing;//是否启用规则:1:未启用,2:启用

	private Integer orderId; // 订单id
	
	private Integer ruleType; // 规则类型增加积分的方式,1.默认为扫码追加积分,2.厂家设置的方式
	
	private Integer eveGetExchangesCount;//每一个参与者能兑换对象数量 0 代表无限制

	private Date createTime; // 创建时间
	
	private Date updateTime;//修改时间
		
	private Integer eveAddIntegralCount; // 每一次积分追加值
	
	private Integer integralActionScope;//作用域:0,针对厂商下所有订单产品,1,指定产品,3,指定订单
	
	private Integer autoFlag;//积分自动扣除和手动扣除,1,手动兑换扣除积分,2积分自动扣除(到指定积分自动兑换指定物品)
	
	private  Date expireTime; // 过期时间
	
	private Date startTime;//启用时间

	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	private String explanation; // 积分说明
	
	public Date getUpdateTime() {
		return updateTime;
	}
	
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getInteRuleId() {
		return inteRuleId;
	}

	public Integer getIsUseing() {
		return isUseing;
	}

	public void setIsUseing(Integer isUseing) {
		this.isUseing = isUseing;
	}

	public void setInteRuleId(Integer inteRuleId) {
		this.inteRuleId = inteRuleId;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getRuleType() {
		return ruleType;
	}

	public void setRuleType(Integer ruleType) {
		this.ruleType = ruleType;
	}

	public Integer getEveGetExchangesCount() {
		return eveGetExchangesCount;
	}

	public void setEveGetExchangesCount(Integer eveGetExchangesCount) {
		this.eveGetExchangesCount = eveGetExchangesCount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getEveAddIntegralCount() {
		return eveAddIntegralCount;
	}

	public void setEveAddIntegralCount(Integer eveAddIntegralCount) {
		this.eveAddIntegralCount = eveAddIntegralCount;
	}

	public Integer getIntegralActionScope() {
		return integralActionScope;
	}

	public void setIntegralActionScope(Integer integralActionScope) {
		this.integralActionScope = integralActionScope;
	}

	public Integer getAutoFlag() {
		return autoFlag;
	}

	public void setAutoFlag(Integer autoFlag) {
		this.autoFlag = autoFlag;
	}




	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	
	

}
