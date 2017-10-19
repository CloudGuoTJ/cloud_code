package com.yunma.model;
import java.io.Serializable;
import java.util.Date;

/**
 * 扫码记录表用于统计商家扫码情况
 */
public class AntiFake implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer rowkey;
    private Integer securityCodeId;
    private String securityCode;
    private Integer productId;
    private String productName;
    private Date scanTime;
    private String scanAddress;
    private Integer userId;
    private String longitude;
    private String latitude;
    private Integer scanCount;
    private Date lastUpdateTime;
    private Integer orderId;//tb_anti_fake表中不存在的字段
    private String openId;

    private String province;
    private String city;
    private String district;

    private int counts;//主页统计的次数
    private double moreMoney = -1; //二维码红包已领取金额（默认-1，未领取状态）
    private int productScanCount;//当天产品扫码次数
    private int isSendRed;//二维码红包是否取现：0、未取现，1、已取现

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

    public Integer getScanCount() {
        return scanCount;
    }

    public void setScanCount(Integer scanCount) {
        this.scanCount = scanCount;
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

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    public double getMoreMoney() {
        return moreMoney;
    }

    public void setMoreMoney(double moreMoney) {
        this.moreMoney = moreMoney;
    }

    public int getProductScanCount() {
        return productScanCount;
    }

    public void setProductScanCount(int productScanCount) {
        this.productScanCount = productScanCount;
    }

    public int getIsSendRed() {
        return isSendRed;
    }

    public void setIsSendRed(int isSendRed) {
        this.isSendRed = isSendRed;
    }
}