package com.yunma.entity.vendorIntegral;

import java.io.Serializable;
import java.sql.Date;

/**
 * 积分兑换对象表tb_vendor_integral_exchanges
 * @author Administrator
 *
 */
public class VendorIntegralExchange implements Serializable{

	



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer inteExchangeId;//设置兑换对象Id
	
	private String exchangesName;//兑换对象名称
	
	private Integer exchangesCount;//兑换对象数量
	
	private Integer integralExchangeCostCount;//兑换所花费积分数量
	
	private String exchangesPicUrl;//如果有实际奖品的话需要传图片,奖品的图片位置
		
	private Integer vendorId;//厂商Id
	
	private Integer status; //使用状态 1开启(默认) 2.关闭
	

	private Integer exchangeType;//兑换类型1,实物(包含红包在内),2,类似优惠券虚拟礼品,3,优质增值服务型,如会员奖励!(前提是商家有特定的会员系统,可进行兑奖)
	
	private Integer orderId;//订单id,拓展值,绑定订单时必填
	
	private Integer ruleId;//进行规则单个绑定时使用,不绑定为通用
	
	private Date  createTime;//创建时间
	
	private Date expireTime;//失效时间
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getInteExchangeId() {
		return inteExchangeId;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public void setInteExchangeId(Integer inteExchangeId) {
		this.inteExchangeId = inteExchangeId;
	}

	public String getExchangesName() {
		return exchangesName;
	}

	public void setExchangesName(String exchangesName) {
		this.exchangesName = exchangesName;
	}

	public Integer getExchangesCount() {
		return exchangesCount;
	}

	public void setExchangesCount(Integer exchangesCount) {
		this.exchangesCount = exchangesCount;
	}

	public Integer getIntegralExchangeCostCount() {
		return integralExchangeCostCount;
	}

	public void setIntegralExchangeCostCount(Integer integralExchangeCostCount) {
		this.integralExchangeCostCount = integralExchangeCostCount;
	}


	public String getExchangesPicUrl() {
		return exchangesPicUrl;
	}

	public void setExchangesPicUrl(String exchangesPicUrl) {
		this.exchangesPicUrl = exchangesPicUrl;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public Integer getExchangeType() {
		return exchangeType;
	}

	public void setExchangeType(Integer exchangeType) {
		this.exchangeType = exchangeType;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
