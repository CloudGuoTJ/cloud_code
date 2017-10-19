package com.yunma.entity.vendorIntegral;

import java.sql.Date;

/**
 * 积分兑奖记录表
 * @author Administrator
 *
 */
public class VendorIntegralExchangeHistory {
	/**
	 
 CREATE TABLE `tb_vendor_player_draw_award_record` (
 
  `hisId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '历史记录ID',
  
  `vendorId` bigint(20) NOT NULL COMMENT '厂商ID',
  
  `orderId` bigint(20) NOT NULL COMMENT '订单ID',
  
  `ruleName` varchar(64) DEFAULT NULL COMMENT '规则名称名称',
  
  `exchangesLv` bigint(20) DEFAULT NULL COMMENT '奖品等级',
  
  `exchangesName` varchar(64) DEFAULT NULL COMMENT '奖品名称',
  
  `exchangesUrl` varchar(64) DEFAULT NULL COMMENT '奖品图片',  
  
  `eveCostIntegralCount` varchar(32) NOT NULL COMMENT '单次兑换所花积分数量',
  
  `openId` varchar(64) NOT NULL COMMENT '中奖微信用户ID与player表中对应',
  
  `recipientName` varchar(20) DEFAULT '' COMMENT '中奖者姓名',
  
  `recipientPhone` varchar(11) DEFAULT '' COMMENT '中奖者电话',
  
  `reveiceTime` datetime DEFAULT NULL COMMENT '奖励领取时间',
  
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  
  `address` varchar(256) DEFAULT NULL COMMENT '地址:用于领取实物奖品从player表中取出',
  
  `getExchangesCount` int(11) DEFAULT 0 COMMENT '兑换次数',
  
  `lastIntegral` int(20) DEFAULT 0 COMMENT'兑奖后的剩余积分积分',
 
 
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='活动中奖纪录表'; 
	 
	
	 */
	
	private Integer hisId; // ID

	private Integer vendorId; // 厂商ID

	private Integer orderId; // 订单ID

	private Integer ruleId; // 规则名称名称

	private Integer exchangesLv; // 奖品等级

	private String exchangesName; // 奖品名称

	private String wxUserUrl; // 奖品图片

	private Integer eveCostIntegralCount; // 兑换奖品所花积分数量

	private String openId; // 中奖微信用户ID与player表中对应

	private String recipientName; // 中奖者姓名

	private String recipientPhone; // 中奖者电话

	private Date reveiceTime; // 奖励领取时间
	
	private Date expireTime;//有效时间

	private Date createTime; // 创建时间

	private String address; // 地址:用于领取实物奖品从player表中取出
	
	private Integer getExchangesCount;//兑奖次数
	
	private Integer lastIntegral;//兑奖后的剩余积分积分
	
	
	private String comment;//备注
	
	private String wxNickName;//微信昵称
	
	

	public String getWxNickName() {
		return wxNickName;
	}

	public void setWxNickName(String wxNickName) {
		this.wxNickName = wxNickName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public Integer getHisId() {
		return hisId;
	}

	public void setHisId(Integer hisId) {
		this.hisId = hisId;
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

	public Integer getExchangesLv() {
		return exchangesLv;
	}

	public void setExchangesLv(Integer exchangesLv) {
		this.exchangesLv = exchangesLv;
	}

	public String getExchangesName() {
		return exchangesName;
	}

	public void setExchangesName(String exchangesName) {
		this.exchangesName = exchangesName;
	}


	public String getWxUserUrl() {
		return wxUserUrl;
	}

	public void setWxUserUrl(String wxUserUrl) {
		this.wxUserUrl = wxUserUrl;
	}

	public Integer getEveCostIntegralCount() {
		return eveCostIntegralCount;
	}

	public void setEveCostIntegralCount(Integer eveCostIntegralCount) {
		this.eveCostIntegralCount = eveCostIntegralCount;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getRecipientName() {
		return recipientName;
	}

	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}


	public String getRecipientPhone() {
		return recipientPhone;
	}

	public void setRecipientPhone(String recipientPhone) {
		this.recipientPhone = recipientPhone;
	}

	public Date getReveiceTime() {
		return reveiceTime;
	}

	public void setReveiceTime(Date reveiceTime) {
		this.reveiceTime = reveiceTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getGetExchangesCount() {
		return getExchangesCount;
	}

	public void setGetExchangesCount(Integer getExchangesCount) {
		this.getExchangesCount = getExchangesCount;
	}

	public Integer getLastIntegral() {
		return lastIntegral;
	}

	public void setLastIntegral(Integer lastIntegral) {
		this.lastIntegral = lastIntegral;
	}
	
	
	
	

}
