package com.yunma.entity.coupon.wechat;

import java.util.Date;

public class WeChatCouponOrderCount {

	private Integer id;
	private Integer vendorId;
	private String vendorName;
	private String orderId;
	private String wxOrderId;
	private String openId;
	private String goodsName;
	private String couponId;
	private String couponName;
	private Double reduce;
	private Double leastCost;
	private Double order_money_1;
	private Date payTime;
	private String payTimeStr;
	private Date updateTime;
	
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
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getWxOrderId() {
		return wxOrderId;
	}
	public void setWxOrderId(String wxOrderId) {
		this.wxOrderId = wxOrderId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getCouponId() {
		return couponId;
	}
	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
	public String getCouponName() {
		return couponName;
	}
	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}
	public Double getReduce() {
		return reduce;
	}
	public void setReduce(Double reduce) {
		this.reduce = reduce;
	}
	public Double getLeastCost() {
		return leastCost;
	}
	public void setLeastCost(Double leastCost) {
		this.leastCost = leastCost;
	}
	public Double getOrder_money_1() {
		return order_money_1;
	}
	public void setOrder_money_1(Double order_money_1) {
		this.order_money_1 = order_money_1;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public String getPayTimeStr() {
		return payTimeStr;
	}
	public void setPayTimeStr(String payTimeStr) {
		this.payTimeStr = payTimeStr;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
