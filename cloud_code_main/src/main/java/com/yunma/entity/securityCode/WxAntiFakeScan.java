package com.yunma.entity.securityCode;

import java.io.Serializable;
import java.util.Date;

/** 微信扫码数量表
 * Created by ${CloudGoo} on 2017/5/9 0009.
 */
public class WxAntiFakeScan implements Serializable{
    private long rowId;
    private long sercurityCodeId;
    private String securityCode;
    private long productId;
    private String productName;
    private Date scanTime;
    private String scanAddress;
    private  String longitude;
    private String latitude;
    private long userId;
    private String openId;
    private Date lastUpdateTime;
    private int scanCount;
    private String province;
    private String city;
    private String district;
    public long getRowId() {
        return rowId;
    }

    public long getSercurityCodeId() {
        return sercurityCodeId;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public Date getScanTime() {
        return scanTime;
    }

    public String getScanAddress() {
        return scanAddress;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public long getUserId() {
        return userId;
    }

    public String getOpenId() {
        return openId;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public int getScanCount() {
        return scanCount;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }



    public void setRowId(long rowId) {
        this.rowId = rowId;
    }

    public void setSercurityCodeId(long sercurityCodeId) {
        this.sercurityCodeId = sercurityCodeId;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setScanTime(Date scanTime) {
        this.scanTime = scanTime;
    }

    public void setScanAddress(String scanAddress) {
        this.scanAddress = scanAddress;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public void setScanCount(int scanCount) {
        this.scanCount = scanCount;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
