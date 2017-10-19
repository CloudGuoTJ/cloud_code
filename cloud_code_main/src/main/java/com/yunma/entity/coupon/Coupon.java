package com.yunma.entity.coupon;

import java.util.Date;

/**
 * Created by Administrator on 2017/6/26.
 */
public class Coupon {
    private String Id;
    private String couponId;
    private String couponName;
    private Date beginTime;
    private Date endTime   ;
    private int style;//1 微信优惠券 2 微店优惠券 3 京东优惠券 4微擎优惠券
    private int couponTotal;
    private int money;
    private int stockCount;
    private Integer vendorId;
    private Integer leastCost;
    private String weidianFetchUrl;
    private String currTimeStr;
    
    private String appid;
    private String secret;
    
	@Override
	public String toString() {
		return "Coupon [Id=" + Id + ", couponId=" + couponId + ", couponName="
				+ couponName + ", beginTime=" + beginTime + ", endTime="
				+ endTime + ", style=" + style + ", couponTotal=" + couponTotal
				+ ", money=" + money + ", stockCount=" + stockCount
				+ ", vendorId=" + vendorId + ", leastCost=" + leastCost
				+ ", weidianFetchUrl=" + weidianFetchUrl + ", currTimeStr="
				+ currTimeStr + ", appid=" + appid + ", secret=" + secret + "]";
	}
	
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
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
	public int getStyle() {
		return style;
	}
	public void setStyle(int style) {
		this.style = style;
	}
	public int getCouponTotal() {
		return couponTotal;
	}
	public void setCouponTotal(int couponTotal) {
		this.couponTotal = couponTotal;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public int getStockCount() {
		return stockCount;
	}
	public void setStockCount(int stockCount) {
		this.stockCount = stockCount;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public Integer getLeastCost() {
		return leastCost;
	}
	public void setLeastCost(Integer leastCost) {
		this.leastCost = leastCost;
	}
	public String getWeidianFetchUrl() {
		return weidianFetchUrl;
	}
	public void setWeidianFetchUrl(String weidianFetchUrl) {
		this.weidianFetchUrl = weidianFetchUrl;
	}
	public String getCurrTimeStr() {
		return currTimeStr;
	}
	public void setCurrTimeStr(String currTimeStr) {
		this.currTimeStr = currTimeStr;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
}
