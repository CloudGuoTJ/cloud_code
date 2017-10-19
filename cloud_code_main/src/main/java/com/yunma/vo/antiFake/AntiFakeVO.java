package com.yunma.vo.antiFake;

import java.io.Serializable;
import java.sql.Date;

public class AntiFakeVO implements Serializable {
	Integer codeType; //二维码类型
	String vendorName;//商家名
	String advert;//广告
	String receviceVendor;//获取商家
	String scanResult;//扫码结果
	String firstScanAddress;//第一次扫码地址
	String firstScanTime;//第一次扫码时间
	private Integer rowkey;//antiFakeId
	private Integer securityCodeId;//二维码ID
	private String securityCode;//二维码
	private Integer productId;//产品ID
	private String productName;//产品名
	private Date scanTime;//扫码
	private String scanAddress;//扫码地址
	private Integer userId;//用户id
	private String longitude;//经度
	private String latitude;//纬度
	private Integer scanCount;//扫码次数
	private Date lastUpdateTime;//最后更新时间
	private Integer orderId;//订单ID

	private int counts;

	public int getCounts() {
		return counts;
	}

	public void setCounts(int counts) {
		this.counts = counts;
	}

	public Integer getRowkey() {
		return rowkey;
	}

	public void setRowkey(Integer rowkey) {
		this.rowkey = rowkey;
	}

	public Integer getSecurityCodeId() {
		return securityCodeId;
	}

	public void setSecurityCodeId(Integer securityCodeId) {
		this.securityCodeId = securityCodeId;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getCodeType() {
		return codeType;
	}

	public void setCodeType(Integer codeType) {
		this.codeType = codeType;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getAdvert() {
		return advert;
	}

	public void setAdvert(String advert) {
		this.advert = advert;
	}

	public String getReceviceVendor() {
		return receviceVendor;
	}

	public void setReceviceVendor(String receviceVendor) {
		this.receviceVendor = receviceVendor;
	}

	public String getScanResult() {
		return scanResult;
	}

	public void setScanResult(String scanResult) {
		this.scanResult = scanResult;
	}

	public Integer getScanCount() {
		return scanCount;
	}

	public void setScanCount(Integer scanCount) {
		this.scanCount = scanCount;
	}

	public String getFirstScanAddress() {
		return firstScanAddress;
	}

	public void setFirstScanAddress(String firstScanAddress) {
		this.firstScanAddress = firstScanAddress;
	}

	public String getFirstScanTime() {
		return firstScanTime;
	}

	public void setFirstScanTime(String firstScanTime) {
		this.firstScanTime = firstScanTime;
	}

}
