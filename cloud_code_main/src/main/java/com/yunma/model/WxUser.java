package com.yunma.model;

import java.io.Serializable;
import java.sql.Date;

/** 微信用户表
 * Created by ${CloudGoo} on 2017/5/3 0003.
 */
public class WxUser implements Serializable{
    private Long userId;
    private Integer  wxConfigVendorId;
    private String openId;
    private String longitude;
    private String latitude;
    private String address;
    private String province;
    private String city;
    private String district;
    private Date  createDate;
    private Integer deleteFlag;
    private Integer scanUser;
    private String nickName;
    private int integral;
    private String  wxAddress;
    private String scanSource;
    private String  headImgurl;
    private Integer sex;
    private String remark;

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public void setScanSource(String scanSource) {

        this.scanSource = scanSource;
    }

    public int getIntegral() {
        return integral;
    }

    public String getScanSource() {
        return scanSource;
    }
    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }


    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setWxConfigVendorId(Integer wxConfigVendorId) {
        this.wxConfigVendorId = wxConfigVendorId;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public void setScanUser(Integer scanUser) {
        this.scanUser = scanUser;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setIntefral(int intefral) {
        this.integral = intefral;
    }

    public void setWxAddress(String wxAddress) {
        this.wxAddress = wxAddress;
    }

    public void setHeadImgurl(String headImgurl) {
        this.headImgurl = headImgurl;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public Long getUserId() {
        return userId;
    }

    public Integer getWxConfigVendorId() {
        return wxConfigVendorId;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getAddress() {
        return address;
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

    public Date getCreateDate() {
        return createDate;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public Integer getScanUser() {
        return scanUser;
    }

    public String getNickName() {
        return nickName;
    }

    public int getIntefral() {
        return integral;
    }

    public String getWxAddress() {
        return wxAddress;
    }

    public String getHeadImgurl() {
        return headImgurl;
    }

    public Integer getSex() {
        return sex;
    }

    public String getRemark() {
        return remark;
    }
}
