package com.yunma.entity.statAntiFakeCount;

import java.io.Serializable;
import java.sql.Date;
/**
 * 二维码扫码地域统计表
 * @author Administrator
 *
 */
public class StatAntiFakeCount implements Serializable{
	private Integer id;//扫码地域信息统计表Id
	private String securityCode;//二维码源码
	private String openId;//扫码微信号
	private Integer vendorId;//商家id
	private Integer orderId;//订单Id
	private String address;//百度util转换的地址
    private String province;//省
    private String city;//市
    private String district;//区
    private String longitude;//经度
    private String latitude;//纬度
    private Date scanTime;
    private String count;//总数
	public String getSecurityCode() {
		return securityCode;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getScanTime() {
		return scanTime;
	}
	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}
	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}


}
