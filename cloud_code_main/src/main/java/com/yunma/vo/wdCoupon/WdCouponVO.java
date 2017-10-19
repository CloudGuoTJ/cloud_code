package com.yunma.vo.wdCoupon;

import java.io.Serializable;
import java.util.Date;

public class WdCouponVO implements Serializable{
	private Integer couponId;//优惠券的id
	private int vendorId;//厂商ID
	private int stock;//优惠券数量
	private int count;//优惠券剩余数量
	private Date beginTime;//开始时间	
	private Date endTime;//结束时间
	private String title;//优惠卷名称
	private  int type;//优惠券类型：1、代金券；2、折扣券；3、礼品券；4、体验券；5、免运费券
	public Integer getCouponId() {
		return couponId;
	}
	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}
	public int getVendorId() {
		return vendorId;
	}
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
}
