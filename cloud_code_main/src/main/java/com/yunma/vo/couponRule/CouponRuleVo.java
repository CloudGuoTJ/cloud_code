package com.yunma.vo.couponRule;

import java.util.Date;

public class CouponRuleVo {

	private Integer id;
	private Integer reduce;   //优惠券面值
	private Integer leastCost;//优惠券最低使用额度
	private Integer stock;	  //库存
	private String state;	  //状态
	private Integer buyerLimit;//领取数量
	private Date startTime;
	private Date endTime;
	
	private Integer vendorId;
	private String vendroName;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getReduce() {
		return reduce;
	}
	public void setReduce(Integer reduce) {
		this.reduce = reduce;
	}
	public Integer getLeastCost() {
		return leastCost;
	}
	public void setLeastCost(Integer leastCost) {
		this.leastCost = leastCost;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getBuyerLimit() {
		return buyerLimit;
	}
	public void setBuyerLimit(Integer buyerLimit) {
		this.buyerLimit = buyerLimit;
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
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public String getVendroName() {
		return vendroName;
	}
	public void setVendroName(String vendroName) {
		this.vendroName = vendroName;
	}
}
