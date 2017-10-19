package com.yunma.entity.coupon.wq;

import java.util.Date;

public class WqCouponVendor {

	private Integer id;
	private Integer vendorId;
	private Integer wqId;
	private String couponName;
	private Double reduce;
	private Double leastCost;
	private String getUrl;
	private Integer total;
	private Integer getmax;
	private String limitdiscounttype;
	private Date createTime;
	private Date startTime;
	private Date endTime;
	private String productUrl;
	
	private String createTimeStr;
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
	public Integer getWqId() {
		return wqId;
	}
	public void setWqId(Integer wqId) {
		this.wqId = wqId;
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
	public String getGetUrl() {
		return getUrl;
	}
	public void setGetUrl(String getUrl) {
		this.getUrl = getUrl;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getGetmax() {
		return getmax;
	}
	public void setGetmax(Integer getmax) {
		this.getmax = getmax;
	}
	public String getLimitdiscounttype() {
		return limitdiscounttype;
	}
	public void setLimitdiscounttype(String limitdiscounttype) {
		this.limitdiscounttype = limitdiscounttype;
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
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
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
	public String getProductUrl() {
		return productUrl;
	}
	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}
	
}
