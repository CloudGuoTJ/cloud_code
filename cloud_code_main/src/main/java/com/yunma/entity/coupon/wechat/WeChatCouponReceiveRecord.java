package com.yunma.entity.coupon.wechat;

import java.util.Date;

/**
 * 微信小店领取记录
 */
public class WeChatCouponReceiveRecord {

	private Integer id;
	private String couponId;
	private String vendorId;
	private String openid;
	private String couponStockId;
	private Date createTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCouponId() {
		return couponId;
	}
	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
