package com.yunma.entity.tracing;

import java.io.Serializable;
import java.util.Date;

/**
 * TracingCodeCustomerScan
 * 消费者溯源扫码记录表
 * @author cloud
 *tb_product_tracing_customer_scan_vendorId
 */

public class TracingCodeCustomerScan implements Serializable{

	private Integer customerTracingScanId;//扫码Id
	
	private String productTracingCode;//溯源码
	
	private Integer productTracingCodeId;//溯源码ID
	
	private Integer vendorId;//溯源码ID
	
	private Integer productId;//产品ID
	
	private String productName;//产品名称
	
	private Date scanTime;//扫描日期
	
	private String scanAddress;//扫描地点
	
	private String longitude;//经度
	
	private String latitude;//纬度
	
	private String openId;//扫描用户Id
	
	private String province;//代理所属省份
	
	private String city;//代理所属城市
	
	private String district;//代理所属地区
	
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public Integer getCustomerTracingScanId() {
		return customerTracingScanId;
	}
	public void setCustomerTracingScanId(Integer customerTracingScanId) {
		this.customerTracingScanId = customerTracingScanId;
	}
	public String getProductTracingCode() {
		return productTracingCode;
	}
	public void setProductTracingCode(String productTracingCode) {
		this.productTracingCode = productTracingCode;
	}
	public Integer getProductTracingCodeId() {
		return productTracingCodeId;
	}
	public void setProductTracingCodeId(Integer productTracingCodeId) {
		this.productTracingCodeId = productTracingCodeId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Date getScanTime() {
		return scanTime;
	}
	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}
	public String getScanAddress() {
		return scanAddress;
	}
	public void setScanAddress(String scanAddress) {
		this.scanAddress = scanAddress;
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
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
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
	
	
	

}
