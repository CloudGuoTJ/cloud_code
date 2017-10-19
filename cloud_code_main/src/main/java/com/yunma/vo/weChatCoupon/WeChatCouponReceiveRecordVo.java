package com.yunma.vo.weChatCoupon;

import java.util.Date;

public class WeChatCouponReceiveRecordVo {
	
	private Integer id;
	private String appId;
	private String mchId;
	private String apiKey;
	private String couponId;
	private Integer vendorId;
	private String openid;
	private String couponStockId;
	private String couponName;
	private Integer reduce;
	private Integer leastCost;
	private Date createTime;
	private String payTime;
	
	private String startTime;
	private String endTime;
	
	@Override
	public String toString() {
		return "WeChatCouponReceiveRecordVo [id=" + id + ", appId=" + appId
				+ ", mchId=" + mchId + ", apiKey=" + apiKey + ", couponId="
				+ couponId + ", vendorId=" + vendorId + ", openid=" + openid
				+ ", couponStockId=" + couponStockId + ", couponName="
				+ couponName + ", reduce=" + reduce + ", leastCost="
				+ leastCost + ", createTime=" + createTime + ", payTime="
				+ payTime + ", startTime=" + startTime + ", endTime=" + endTime
				+ "]";
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getMchId() {
		return mchId;
	}
	public void setMchId(String mchId) {
		this.mchId = mchId;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public String getCouponId() {
		return couponId;
	}
	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getCouponStockId() {
		return couponStockId;
	}
	public void setCouponStockId(String couponStockId) {
		this.couponStockId = couponStockId;
	}
	public String getCouponName() {
		return couponName;
	}
	public void setCouponName(String couponName) {
		this.couponName = couponName;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	
}
