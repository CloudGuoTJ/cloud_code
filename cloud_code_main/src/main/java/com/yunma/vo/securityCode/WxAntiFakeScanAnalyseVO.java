package com.yunma.vo.securityCode;

/**
 * Created by ${CloudGoo} on 2017/5/9 0009.
 */
public class WxAntiFakeScanAnalyseVO {
    private String scanTime;
    private String scanAddress;
    private Integer orderId;

    public String getScanTime() {
        return scanTime;
    }

    public void setScanTime(String scanTime) {
        this.scanTime = scanTime;
    }

    public String getScanAddress() {
        return scanAddress;
    }

    public void setScanAddress(String scanAddress) {
        this.scanAddress = scanAddress;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}
