package com.yunma.vo.securityCode;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ${CloudGoo} on 2017/5/9 0009.
 */
public class WxAntiFakeScanVO implements Serializable {

    Integer  codeType;
    String  vendorName;
    String  advert;
    String  receviceVendor;
    String  scanResult;
    String  firstScanAddress;
    String  firstScanTime;
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
    private Integer orderId;

    private  int counts;

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
