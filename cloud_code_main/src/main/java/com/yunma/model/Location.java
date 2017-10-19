package com.yunma.model;

import java.io.Serializable;

/**
 * 预存微信扫码信息表地理位置信息
 * Created by ${CloudGoo} on 2017/5/9 0009.
 */
public class Location implements Serializable{
	private String address;
    private String province;
    private String city;
    private String district;
    private String longitude;
    private String latitude;

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
}
